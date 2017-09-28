namespace java com.heepay.manage.rpc.service

struct ChannelPatternAndChannelTypeThrift{
    1:string value, //数据值
    2:string label  //标签名
}

service ChannelPatternAndChannelTypeService {
  list<ChannelPatternAndChannelTypeThrift> queryListByType(1:string type),
  string queryChannelPatternChannelTypeByType(1:string type,2:string label)
}
