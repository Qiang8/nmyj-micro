
micro base jar需先下载工程，本地install。

maven profile选local

工程启动顺序

1.启动ServerEurekaApplication。
2.启动ServerOAuth2Application。
3.启动ServerZuulApplication。
4.启动MicroBaseSampleApplication。
5.启动WebBaseSampleApplication。
6.启动WebBaseSampleApplication。
7.启动SsoClientHtmlApplication。

访问http://localhost:9003/application-base-sample/
访问http://localhost:9003/sso-client-html

====================================

####一、启动

#####1.启动ServerEurekaApplication。
#####2.启动ServerOAuth2Application。
#####3.启动ServerZuulApplication。
#####4.启动SsoResourceApplication。

####二、访问、测试

http://localhost:9003/auth/oauth/token?grant_type=password&username=god&password=1
authorization basic auth : 
    username:clientId
    password:secret
```$xslt
{
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzYyNjA5OTYsInVzZXJfbmFtZSI6ImdvZCIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiIzZTdmYjBjYS1mNmZjLTRmN2YtOTAwMi02Yzk5YTYyNTdiOGEiLCJjbGllbnRfaWQiOiJjbGllbnRJZCIsInNjb3BlIjpbInVzZXJfaW5mbyIsInJlYWQiLCJ3cml0ZSIsImZvbyIsImJhciIsInNlcnZlciJdfQ.-b-xPtpLNEHftMMr-AC-PHsXQlzgIvXZAMGyjcfx2sU",
    "token_type": "bearer",
    "expires_in": 43199,
    "scope": "user_info read write foo bar server",
    "jti": "3e7fb0ca-f6fc-4f7f-9002-6c99a6257b8a"
}
```    

http://localhost:9003/auth/user/me?access_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzYyNjA5OTYsInVzZXJfbmFtZSI6ImdvZCIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiIzZTdmYjBjYS1mNmZjLTRmN2YtOTAwMi02Yzk5YTYyNTdiOGEiLCJjbGllbnRfaWQiOiJjbGllbnRJZCIsInNjb3BlIjpbInVzZXJfaW5mbyIsInJlYWQiLCJ3cml0ZSIsImZvbyIsImJhciIsInNlcnZlciJdfQ.-b-xPtpLNEHftMMr-AC-PHsXQlzgIvXZAMGyjcfx2sU

```$xslt
{
    "authorities": [
        {
            "authority": "ROLE_USER"
        }
    ],
    "details": {
        "remoteAddress": "127.0.0.1",
        "sessionId": null,
        "tokenValue": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzYyNjA5OTYsInVzZXJfbmFtZSI6ImdvZCIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiIzZTdmYjBjYS1mNmZjLTRmN2YtOTAwMi02Yzk5YTYyNTdiOGEiLCJjbGllbnRfaWQiOiJjbGllbnRJZCIsInNjb3BlIjpbInVzZXJfaW5mbyIsInJlYWQiLCJ3cml0ZSIsImZvbyIsImJhciIsInNlcnZlciJdfQ.-b-xPtpLNEHftMMr-AC-PHsXQlzgIvXZAMGyjcfx2sU",
        "tokenType": "Bearer",
        "decodedDetails": null
    },
    "authenticated": true,
    "userAuthentication": {
        "authorities": [
            {
                "authority": "ROLE_USER"
            }
        ],
        "details": null,
        "authenticated": true,
        "principal": "god",
        "credentials": "N/A",
        "name": "god"
    },
    "oauth2Request": {
        "clientId": "clientId",
        "scope": [
            "user_info",
            "read",
            "write",
            "foo",
            "bar",
            "server"
        ],
        "requestParameters": {
            "client_id": "clientId"
        },
        "resourceIds": [],
        "authorities": [],
        "approved": true,
        "refresh": false,
        "redirectUri": null,
        "responseTypes": [],
        "extensions": {},
        "grantType": null,
        "refreshTokenRequest": null
    },
    "principal": "god",
    "credentials": "",
    "clientOnly": false,
    "name": "god"
}
```

http://localhost:9003/sso-resource-service/hello?access_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzYyNjA5OTYsInVzZXJfbmFtZSI6ImdvZCIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiIzZTdmYjBjYS1mNmZjLTRmN2YtOTAwMi02Yzk5YTYyNTdiOGEiLCJjbGllbnRfaWQiOiJjbGllbnRJZCIsInNjb3BlIjpbInVzZXJfaW5mbyIsInJlYWQiLCJ3cml0ZSIsImZvbyIsImJhciIsInNlcnZlciJdfQ.-b-xPtpLNEHftMMr-AC-PHsXQlzgIvXZAMGyjcfx2sU

```$xslt
{
    "hello": "world"
}
```
