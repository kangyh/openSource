$(function(){
     var init=function(uploader,filePicker,dndArea){
		var $wrap = $('#'+uploader),
            // 图片容器
            $queue = $( '<ul class="filelist"></ul>' )
           .appendTo( $wrap.find( '.queueList' ) ),

            // 状态栏，包括进度和控制按钮
            $statusBar = $wrap.find( '.statusBar' ),

            // 文件总体选择信息。
            $info = $statusBar.find( '.info' ),

            // 上传按钮
            $upload = $wrap.find( '.uploadBtn' ),

            // 没选择文件之前的内容。
            $placeHolder = $wrap.find( '.placeholder' ),

            $progress = $statusBar.find( '.progress' ).hide(),

            // 添加的文件数量
            fileCount = 0,

            // 添加的文件总大小
            fileSize = 0,

            // 优化retina, 在retina下这个值是2
            ratio = window.devicePixelRatio || 1,

            // 缩略图大小
            thumbnailWidth = 150 * ratio,
            thumbnailHeight = 90 * ratio,

            // 可能有pedding, ready, uploading, confirm, done.
            state = 'pedding',

            // 所有文件的进度信息，key为file id
            percentages = {},
            // 判断浏览器是否支持图片的base64
            isSupportBase64 = ( function() {
                var data = new Image();
                var support = true;
                data.onload = data.onerror = function() {
                    if( this.width != 1 || this.height != 1 ) {
                        support = false;
                    }
                }
                data.src = "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw==";
                return support;
            } )(),

            // 检测是否已经安装flash，检测flash的版本
            flashVersion = ( function() {
                var version;

                try {
                    version = navigator.plugins[ 'Shockwave Flash' ];
                    version = version.description;
                } catch ( ex ) {
                    try {
                        version = new ActiveXObject('ShockwaveFlash.ShockwaveFlash')
                                .GetVariable('$version');
                    } catch ( ex2 ) {
                        version = '0.0';
                    }
                }
                version = version.match( /\d+/g );
                return parseFloat( version[ 0 ] + '.' + version[ 1 ], 10 );
            } )(),

            supportTransition = (function(){
                var s = document.createElement('p').style,
                    r = 'transition' in s ||
                            'WebkitTransition' in s ||
                            'MozTransition' in s ||
                            'msTransition' in s ||
                            'OTransition' in s;
                s = null;
                return r;
            })(),

            // WebUploader实例
            uploader;

        if ( !WebUploader.Uploader.support('flash') && WebUploader.browser.ie ) {

            // flash 安装了但是版本过低。
            if (flashVersion) {
                (function(container) {
                    window['expressinstallcallback'] = function( state ) {
                        switch(state) {
                            case 'Download.Cancelled':
                                alert('您取消了更新！')
                                break;

                            case 'Download.Failed':
                                alert('安装失败')
                                break;

                            default:
                                alert('安装已成功，请刷新！');
                                break;
                        }
                        delete window['expressinstallcallback'];
                    };

                    var swf = './expressInstall.swf';
                    // insert flash object
                    var html = '<object type="application/' +
                            'x-shockwave-flash" data="' +  swf + '" ';

                    if (WebUploader.browser.ie) {
                        html += 'classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" ';
                    }

                    html += 'width="100%" height="100%" style="outline:0">'  +
                        '<param name="movie" value="' + swf + '" />' +
                        '<param name="wmode" value="transparent" />' +
                        '<param name="allowscriptaccess" value="always" />' +
                    '</object>';

                    container.html(html);

                })($wrap);

            // 压根就没有安转。
            } else {
                $wrap.html('<a href="http://www.adobe.com/go/getflashplayer" target="_blank" border="0"><img alt="get flash player" src="http://www.adobe.com/macromedia/style_guide/images/160x41_Get_Flash_Player.jpg" /></a>');
            }

            return;
        } else if (!WebUploader.Uploader.support()) {
            alert( 'Web Uploader 不支持您的浏览器！');
            return;
        }

        var agentProjectName = $("#agent_project_name").val();
        // 实例化
        uploader = WebUploader.create({
            pick: {
                id: '#'+filePicker,
                label: '',
				multiple:false
            },
            formData: {
                //uid: 123
            	isThumb:1,
            	imgType:2
            },
            dnd: '#'+dndArea,
            paste: '#'+uploader,
            swf: '/Uploader.swf',
            chunked: false,
            chunkSize: 512 * 1024,
            server: agentProjectName+"/upload",
            auto :true,
             accept: {
                 title: 'Images',
                 extensions: 'gif,jpg,jpeg,bmp,png',
                 mimeTypes: 'image/*'
             },

            // 禁掉全局的拖拽功能。这样不会出现图片拖进页面的时候，把图片打开。
            disableGlobalDnd: true,
            fileNumLimit: 1,
            fileSizeLimit: 200 * 1024 * 1024,    // 200 M
			//验证单个文件大小
            fileSingleSizeLimit: 2 * 1024 * 1024    // 2 M
        });

        // 添加“添加文件”的按钮，
        uploader.addButton({
            id: '#filePicker2',
            label: '继续添加'
        });

        uploader.on('ready', function() {
            window.uploader = uploader;
        });

        // 当有文件添加进来时执行，负责view的创建
        function addFile( file ) {
            var $li = $( '<li id="' + file.id + '">' +
                    '<p class="imgWrap"></p>'+
					'<p class="ac_info_remove"><img src='+agentProjectName+'"/static/images/del.png"></p>'+
                    '</li>' ),

                $btns = $('<div class="file-panel">' +
                    '<span class="cancel">删除</span>' +
                    '<span class="rotateRight">向右旋转</span>' +
                    '<span class="rotateLeft">向左旋转</span></div>').appendTo( $li ),
                $prgress = $li.find('p.progress span'),
                $wrap = $li.find( 'p.imgWrap' ),
                $info = $('<p class="error"></p>'),
                showError = function( code ) {
                    switch( code ) {
                        case 'exceed_size':
                            text = '文件大小超出';
                            break;

                        case 'interrupt':
                            text = '上传暂停';
                            break;

                        default:
                            text = '上传失败，请重试';
                            break;
                    }

                    $info.text( text ).appendTo( $li );
                };
            if ( file.getStatus() === 'invalid' ) {
                showError( file.statusText );
            } else {
                // @todo lazyload
                $wrap.text( '预览中' );
                uploader.makeThumb( file, function( error, src ) {
                    var img;

                    if ( error ) {
                        $wrap.text( '不能预览' );
                        return;
                    }

                    if( isSupportBase64 ) {
                        img = $('<img src="'+src+'">');
                        $wrap.empty().append( img );
                    } else {
                        $.ajax(agentProjectName+'/upload', {
                            method: 'POST',
                            data: src,
                            dataType:'json'
                        }).done(function( response ) {
                            if (response.result) {
                                img = $('<img src="'+response.result+'">');
                                $wrap.empty().append( img );
                            } else {
                                $wrap.text("预览出错");
                            }
                        });
                    }
                }, thumbnailWidth, thumbnailHeight );

                percentages[ file.id ] = [ file.size, 0 ];
                file.rotation = 0;
            }
            file.on('statuschange', function( cur, prev ) {
                if ( prev === 'progress' ) {
                    $prgress.hide().width(0);
                } else if ( prev === 'queued' ) {
                    $li.off( 'mouseenter mouseleave' );
                    $btns.remove();
                }

                // 成功
                if ( cur === 'error' || cur === 'invalid' ) {
                    showError( file.statusText );
                    percentages[ file.id ][ 1 ] = 1;
                } else if ( cur === 'interrupt' ) {
                    showError( 'interrupt' );
                } else if ( cur === 'queued' ) {
                    percentages[ file.id ][ 1 ] = 0;
                } else if ( cur === 'progress' ) {
                    $info.remove();
                    $prgress.css('display', 'block');
                } else if ( cur === 'complete' ) {
                    $li.append( '<span class="success"></span>' );
                }

                $li.removeClass( 'state-' + prev ).addClass( 'state-' + cur );
            });

            $li.on( 'mouseenter', function() {
                $btns.stop().animate({height: 30});
            });

            $li.on( 'mouseleave', function() {
                $btns.stop().animate({height: 0});
            });

            //删除
            $li.on( 'click','p', function() {
                var index = $(this).index(),
                    deg;
                switch ( index ) {
                    case 1:
                        uploader.removeFile( file );

                        return;
                }

            });

            $btns.on( 'click', 'span', function() {
                var index = $(this).index(),
                    deg;
					alert(index);

                switch ( index ) {
                    case 0:
                        uploader.removeFile( file );
                        return;

                    case 1:
                        file.rotation += 90;
                        break;

                    case 2:
                        file.rotation -= 90;
                        break;
                }

                if ( supportTransition ) {
                    deg = 'rotate(' + file.rotation + 'deg)';
                    $wrap.css({
                        '-webkit-transform': deg,
                        '-mos-transform': deg,
                        '-o-transform': deg,
                        'transform': deg
                    });
                } else {
                    $wrap.css( 'filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation='+ (~~((file.rotation/90)%4 + 4)%4) +')');
                    // use jquery animate to rotation
                    // $({
                    //     rotation: rotation
                    // }).animate({
                    //     rotation: file.rotation
                    // }, {
                    //     easing: 'linear',
                    //     step: function( now ) {
                    //         now = now * Math.PI / 180;

                    //         var cos = Math.cos( now ),
                    //             sin = Math.sin( now );

                    //         $wrap.css( 'filter', "progid:DXImageTransform.Microsoft.Matrix(M11=" + cos + ",M12=" + (-sin) + ",M21=" + sin + ",M22=" + cos + ",SizingMethod='auto expand')");
                    //     }
                    // });
                }


            });

            $li.appendTo( $queue );

        }

        // 负责view的销毁
        function removeFile( file ) {
            var $li = $('#'+file.id);

            delete percentages[ file.id ];
            updateTotalProgress();
            $li.off().find('.file-panel').off().end().remove();
        }

        function updateTotalProgress() {
            var loaded = 0,
                total = 0,
                spans = $progress.children(),
                percent;

            $.each( percentages, function( k, v ) {
                total += v[ 0 ];
                loaded += v[ 0 ] * v[ 1 ];
            } );

            percent = total ? loaded / total : 0;


            spans.eq( 0 ).text( Math.round( percent * 100 ) + '%' );
            spans.eq( 1 ).css( 'width', Math.round( percent * 100 ) + '%' );
            updateStatus();
        }

        function updateStatus() {
            var text = '', stats;

            if ( state === 'ready' ) {
                text = '选中' + fileCount + '张图片，共' +
                        WebUploader.formatSize( fileSize ) + '。';
            } else if ( state === 'confirm' ) {
                stats = uploader.getStats();
                if ( stats.uploadFailNum ) {
                    text = '已成功上传' + stats.successNum+ '张照片至XX相册，'+
                        stats.uploadFailNum + '张照片上传失败，<a class="retry" href="#">重新上传</a>失败图片或<a class="ignore" href="#">忽略</a>'
                }

            } else {
                stats = uploader.getStats();
                text = '共' + fileCount + '张（' +
                        WebUploader.formatSize( fileSize )  +
                        '），已上传' + stats.successNum + '张';

                if ( stats.uploadFailNum ) {
                    text += '，失败' + stats.uploadFailNum + '张';
                }
            }

            $info.html( text );
        }

        function setState( val ) {
            var file, stats;

            if ( val === state ) {
                return;
            }

            $upload.removeClass( 'state-' + state );
            $upload.addClass( 'state-' + val );
            state = val;

            switch ( state ) {
                case 'pedding':
                    $placeHolder.removeClass( 'element-invisible' );
                    $queue.hide();
                    $statusBar.addClass( 'element-invisible' );
                    uploader.refresh();
                    break;

                case 'ready':
                    $placeHolder.addClass( 'element-invisible' );
                    $( '#filePicker2' ).removeClass( 'element-invisible');
                    $queue.show();
                    $statusBar.removeClass('element-invisible');
                    uploader.refresh();
                    break;

                case 'uploading':
                    $( '#filePicker2' ).addClass( 'element-invisible' );
                    $progress.show();
                    $upload.text( '暂停上传' );
                    break;

                case 'paused':
                    $progress.show();
                    $upload.text( '继续上传' );
                    break;

                case 'confirm':
                    $progress.hide();
                    $( '#filePicker2' ).removeClass( 'element-invisible' );
                    $upload.text( '开始上传' );

                    stats = uploader.getStats();
                    if ( stats.successNum && !stats.uploadFailNum ) {
                        setState( 'finish' );
                        return;
                    }
                    break;
                case 'finish':
                    stats = uploader.getStats();
                    if ( stats.successNum ) {
                        //alert( '上传成功' );
                    } else {
                        // 没有成功的图片，重设
                        state = 'done';
                        location.reload();
                    }
                    break;
            }

            updateStatus();
        }

       

        uploader.onFileQueued = function( file ) {
            fileCount++;
            fileSize += file.size;

            if ( fileCount === 1 ) {
                $placeHolder.addClass( 'element-invisible' );
                $statusBar.show();
            }

            addFile( file );
            setState( 'ready' );
            updateTotalProgress();
        };

        uploader.onFileDequeued = function( file ) {
            fileCount--;
            fileSize -= file.size;

            if ( !fileCount ) {
                setState( 'pedding' );
            }

            removeFile( file );
            updateTotalProgress();

        };

        uploader.on( 'all', function( type ) {
            var stats;
            switch( type ) {
                case 'uploadFinished':
                    setState( 'confirm' );
                    break;

                case 'startUpload':
                    setState( 'uploading' );
                    break;

                case 'stopUpload':
                    setState( 'paused' );
                    break;

            }
        });

        uploader.onError = function( code ) {
			
			switch( code ) {
				case 'F_EXCEED_SIZE':
					var text = '文件大小超出2M';
					break;

				case 'Q_TYPE_DENIED':
					var text = '不符合上传格式';
					break;

				default:
					var text = code;
					break;
			}
            $('#cover').height($(document).height());
            $("#cover").show();
            $(".pop_upload_message").show();
            $(".upload_text_tip").text(text);
            $("html,body").animate({scrollTop:0},500);
        };

        $upload.on('click', function() {
            if ( $(this).hasClass( 'disabled' ) ) {
                return false;
            }

            if ( state === 'ready' ) {
                uploader.upload();
            } else if ( state === 'paused' ) {
                uploader.upload();
            } else if ( state === 'uploading' ) {
                uploader.stop();
            }
        });

        $info.on( 'click', '.retry', function() {
            uploader.retry();
        } );

        $info.on( 'click', '.ignore', function() {
            alert( 'todo' );
        } );

        $upload.addClass( 'state-' + state );
        updateTotalProgress();
        var data_name=null;
        $wrap.on("click",function(){
           data_name=$(this).attr("data-name");
        });
		uploader.onUploadSuccess=function(file,res){
            var dynamicBasePath = $("#dynamicBasePath").val();
             if(data_name=="uploader"){
                 if(res.code == 0){
                     uploader.onError("营业执照,上传失败！");
                     return false;
                 }
             	$("#img1").val(dynamicBasePath + "display/business_license_file");
                 $("#img1sub").val(res.path);

     	        }else if(data_name=="uploader_two"){
                 if(res.code == 0){
                     uploader.onError("组织机构代码,上传失败！");
                     return false;
                 }
     	        	$("#img2").val(dynamicBasePath + "display/organization_code_certificate");
                 $("#img2sub").val(res.path);
     	        }else if(data_name=="uploader_three"){
                 if(res.code == 0){
                     uploader.onError("税务登记证,上传失败！");
                     return false;
                 }
     	        	$("#img3").val(dynamicBasePath + "display/tax_registration_certificate");
                 $("#img3sub").val(res.path);
     	        }else if(data_name=="uploader_four"){
                 if(res.code == 0){
                     uploader.onError("开户许可证,上传失败！");
                     return false;
                 }
     	        	$("#img4").val(dynamicBasePath + "display/permits_accounts");
                 $("#img4sub").val(res.path);
     	        }else if(data_name=="uploader_five"){
                 if(res.code == 0){
                     uploader.onError("个人信息页,上传失败！");
                     return false;
                 }
                 $("#img5").val(dynamicBasePath + "display/legal_certificate_front");
                 $("#img5sub").val(res.path);
     	        }else if(data_name=="uploader_sex"){
                 if(res.code == 0){
                     uploader.onError("国徽页,上传失败！");
                     return false;
                 }
                 $("#img6").val(dynamicBasePath + "display/legal_certificate_back");
                 $("#img6sub").val(res.path);
     	        }
		};
   }
init("uploader","filePicker","dndArea");
init("uploader","filePicker","dndArea");
init("uploader_two","filePicker_two","dndArea_two");
init("uploader_three","filePicker_three","dndArea_three");
init("uploader_four","filePicker_four","dndArea_four");
init("uploader_five","filePicker_five","dndArea_five");
init("uploader_sex","filePicker_sex","dndArea_sex");

    var img_one = $("#img1").val();
    var img_two = $("#img2").val();
    var img_three = $("#img3").val();
    var img_four = $("#img4").val();
    var img_five = $("#img5").val();
    var img_sex = $("#img6").val();
    function imgeidt(img_url,objone,objtwo){
        //编辑
        if(img_url){
            $("#"+objone).hide();
            var _html ='<p class="imgWrap"><img src='+img_url+'></p>'+
                '<p class="ac_info_remove"><img src='+ agentProjectName + '"/static/images/del.png"></p></p>';
            $("."+objtwo).html(_html);
            $(".filelist").html("<li style='display:none'></li>");
        }else{
            $("."+objtwo).hide();
        }
    };
    imgeidt(img_one,'filePicker','upload_edit_one');
    imgeidt(img_two,'filePicker_two','upload_edit_two');
    imgeidt(img_three,'filePicker_three','upload_edit_three');
    imgeidt(img_four,'filePicker_four','upload_edit_four');
    imgeidt(img_five,'filePicker_five','upload_edit_five');
    imgeidt(img_sex,'filePicker_sex','upload_edit_sex');
    /*删除*/
    $(".number_img_edit").on("click",".ac_info_remove",function(){
        $(this).parent(".number_img_edit").hide();
        $(this).parent().siblings(".placeholder").find("div:eq(0)").show();
    });


	$(".upload_next").on("click",function(){
	    alert("hello");
		var img1sub = $("#img1sub").val();
		var img2sub = $("#img2sub").val();
		var img3sub = $("#img3sub").val();
		var img4sub = $("#img4sub").val();
		var img5sub = $("#img5sub").val();
		var img6sub = $("#img6sub").val();
        if(img1sub==""){
            $('#cover').height($(document).height());
            $("#cover").show();
            $(".pop_upload_message").show();
            $(".upload_text_tip").text("请上传营业执照！");
            $("html,body").animate({scrollTop:0},500);
            return false;
        }
        if(img2sub==""){
            $('#cover').height($(document).height());
            $("#cover").show();
            $(".pop_upload_message").show();
            $(".upload_text_tip").text("请上传组织机构代码！");
            $("html,body").animate({scrollTop:0},500);
            return false;
        }
        if(img3sub==""){
            $('#cover').height($(document).height());
            $("#cover").show();
            $(".pop_upload_message").show();
            $(".upload_text_tip").text("请上传税务登记证！");
            $("html,body").animate({scrollTop:0},500);
            return false;
        }
        if(img4sub==""){
            $('#cover').height($(document).height());
            $("#cover").show();
            $(".pop_upload_message").show();
            $(".upload_text_tip").text("请上传开户许可证！");
            $("html,body").animate({scrollTop:0},500);
            return false;
        }
        if(img5sub==""){
            $('#cover').height($(document).height());
            $("#cover").show();
            $(".pop_upload_message").show();
            $(".upload_text_tip").text("请上传法定代表人证件照片个人信息页！");
            $("html,body").animate({scrollTop:0},500);
            return false;
        }
        if(img6sub==""){
            $('#cover').height($(document).height());
            $("#cover").show();
            $(".pop_upload_message").show();
            $(".upload_text_tip").text("请上传法定代表人证件照片国徽页！");
            $("html,body").animate({scrollTop:0},500);
            return false;
        }
        $("#cover_two").height($(document).height());
        $("#cover_two").show();
        $(".loading").show();
        $("html,body").animate({scrollTop:0},500);
        $("#form").submit();
	});
    /*获取滚动条高度*/
    var st = null;
    $(window).scroll(function (){
        st = $(this).scrollTop();
        var offsetheight =  $(".pop_upload_box").outerHeight();
        $(".pop_upload_box").css({"top":($(window).height())/2+st-offsetheight/2+"px"});
    });

    /*点击预览看大图*/
    $(".js_pop_upload").on("click",function(){
        var name = $(this).attr("data-name");
        var imgurl = $(this).attr("data-img");
        $('#cover').height($(document).height());
        $("#cover").show();
        $(".pop_upload_box").show();
        $(".pop_upload_box h3 em").text(name);
        $(".pop_upload_box p img").attr("src",imgurl);
        setTimeout(function(){
            var offsetheight =  $(".pop_upload_box").outerHeight();
            $(".pop_upload_box").css({"top":($(window).height())/2+st-offsetheight/2+"px"});
        },100);

        $(document.body).css("overflow","hidden");
    });

    $(".pop_upload_box h3 span").on("click",function(){
        $("#cover").hide();
        $(".pop_upload_box").hide();
        $(document.body).css("overflow","");
    });
    $(".pop_hd img").on("click",function(){
        $("#cover").hide();
        $(".pop_upload_message").hide();
    });
});