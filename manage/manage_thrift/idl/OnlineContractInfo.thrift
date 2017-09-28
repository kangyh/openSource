namespace java com.heepay.manage.rpc.service

struct OnlineContractInfoThrift {
    1:string id,//表自增id
    2:string userId, //商户id
    3:string normProductCode,//标准产品编码
    4:string url,//网址
    5:string notifyUrl,//异步通知地址
    6:string backUrl,//同步返回地址
    7:string ipDomain,//ip/域名
    8:string file1,//应用1在FastDFS的地址
    9:string file2,//应用2在FastDFS的地址
    10:string file3,//应用3在FastDFS的地址
    11:string file4,//应用4在FastDFS的地址
    12:string file5,//应用5在FastDFS的地址
    13:string status,//状态
    14:string createTime,//创建时间
    15:string passTime,//审核通过时间
    16:string successTime,//验证成功时间
    17:string contractTime,//签约时间
    18:string rejectTime,//审核拒绝时间
    19:string productName,//中文名字
    20:string rate,//签约费率
    21:string name,//用于修改时的名字
    22:string times,//审核失败次数
    23:string originalFilePath, //生成的合同存储地址
    24:string middleFilePath,   //盖了商户章的文件存储地址
    25:string finalFilePath,    //有效的合同文件地址
    26:string batchNo,          //批次号
    27:string size              //数量，在页面显示用
}

service OnlineContractInfoService {
    // 根据用户id和产品代码查询指定的产品是否有记录
    string queryProductIsOpenByProductCode(1:string userId,2:string productCode),
    // 根据用户id查询该用户在签约表中的未完成签约的所有产品情况（签约页面显示）
    list<OnlineContractInfoThrift> queryUnfinishedProductInfoByUserId(1:string userId),
    // 根据用户id查询该用户在签约表中的签约的所有产品情况（新增签约页面增加标识）
    list<OnlineContractInfoThrift> queryAllProductInfoByUserId(1:string userId),
    // 新增一个申请批次的产品
    string insertNewProduct(1:list<OnlineContractInfoThrift> thriftList),
    // 查询批次数量根据用户id
    i32 queryProductsBatchByUserId(1:string userId),
    // 根据批次号查询当前批次的所有新增产品列表
    list<OnlineContractInfoThrift> queryProductsBybatchNoAndUserId(1:string batchNo,2:string userId),
    // 修改一个失败的产品
    string updateSignFailProduct(1:list<OnlineContractInfoThrift> thriftList,2:string batchNo),
    // 查询指定产品，根据用户id和产品代码
    OnlineContractInfoThrift queryProductByUserIdAndProductCode(1:string userId,2:string productCode),
    // 查询商户已开通产品
    string queryProductIsOpen(1:string userId)
}