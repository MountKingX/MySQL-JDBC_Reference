curl localhost:8080/users' -v

curl --request POST localhost:8080/users -H "Content-Type:application/json" -d '{"id":3, "username":"test2", "password":"test2", "userRole":"ADMIN", "nickName":"test2"}'

curl --request POST localhost:8080/users -H "Content-Type:application/json" -d '{"username":"test4", "password":"test4", "userRole":"ADMIN", "nickName":"test4"}' -v
