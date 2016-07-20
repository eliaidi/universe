# universe

universe 是对常用的 java 框架进行多一层的封装，来提高使用框架的灵活性，让开发更加的方便。

## map-validtion

map-validtion 是对 Map 对象的一个验证器，主要的作用是验证 Map 的数据有效性。

maven:

    <dependency>
      <groupId>com.github.dactiv.universe</groupId>
      <artifactId>map-validation</artifactId>
      <version>1.0.0-SNAPSHOT</version>
    </dependency>

## shiro-extension

shiro-extension 是对 apache shiro 安全框架的一个扩展，主要包含扩展内容为：验证码登录、redis 做 session、认证、授权的缓存。

maven:

    <dependency>
      <groupId>com.github.dactiv.universe</groupId>
      <artifactId>shiro-extension</artifactId>
      <version>1.0.0-SNAPSHOT</version>
    </dependency>
    
## captcha-manager

captcha-manager 是对 shiro-extension 的一个验证码管理，简单的实现一些验证码生成过程的一个 jar。

maven:

    <dependency>
      <groupId>com.github.dactiv.universe</groupId>
      <artifactId>captcha-manager</artifactId>
      <version>1.0.0-SNAPSHOT</version>
    </dependency>

## spring-mvc-extension

spring-mvc-extension 是对 spring mvc 框架的一个扩展，扩展内容不多，目前对泛型 List 参数提供支持。

maven:

    <dependency>
      <groupId>com.github.dactiv.universe</groupId>
      <artifactId>shiro-extension</artifactId>
      <version>1.0.0-SNAPSHOT</version>
    </dependency>

## sso

sso 是采用 shiro 封装的单点登录框架，该框架包含 sso-server 和 sso-client。sso-server 是服务端需要的 jar 包，sso-client 值客户端需要的 jar 包。

maven:

    <dependency>
      <groupId>com.github.dactiv.universe</groupId>
      <artifactId>sso-server</artifactId>
      <version>1.0.0-SNAPSHOT</version>
    </dependency>

    <dependency>
      <groupId>com.github.dactiv.universe</groupId>
      <artifactId>sso-client</artifactId>
      <version>1.0.0-SNAPSHOT</version>
    </dependency>

## freemarker-extension

freemarker-extension 是对 freemarker 框架的一个扩展，扩展内容不多，目前编写了一个远程模板调用的加载器。

maven:

    <dependency>
      <groupId>com.github.dactiv.universe</groupId>
      <artifactId>freemarker-extension</artifactId>
      <version>1.0.0-SNAPSHOT</version>
    </dependency>

    ## freemarker-extension

session-manager 是对 session 管理的一个扩展，可将 session 信息存储在指定的位置中，如:redis。

maven:

    <dependency>
      <groupId>com.github.dactiv.universe</groupId>
      <artifactId>session-manager</artifactId>
      <version>1.0.0-SNAPSHOT</version>
    </dependency>

jdbc-extension 是对 jdbc 层的一个封装，主要负责读写分离，分表分库等操作。

maven:

    <dependency>
      <groupId>com.github.dactiv.universe</groupId>
      <artifactId>jdbc-extension</artifactId>
      <version>1.0.0-SNAPSHOT</version>
    </dependency>
