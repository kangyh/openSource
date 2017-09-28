namespace java com.heepay.manage.rpc.service

struct Response {
  1:string code,
  2:string msg
}

service FileStorageService {
    Response WriteFile(1:string file_name, 2:string schema, 3:binary write_buffer, 4:i64 length)
}