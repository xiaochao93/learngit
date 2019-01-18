## 对啊网种子项目

### 使用说明
1. 修改```WebControllerAdvice.java```中```@ControllerAdvice()```中的扫描包名
2. 修改```RestControllerAdvice.java```中```@RestControllerAdvice()```中的扫描包名
3. 修改```application.properties```中```mybatis.type-aliases-package```中的扫描包

### 项目结构

#### 本项目使用springboot为主要框架基础，版本为2.0。请提前阅读相关文档。
目录结构
```
 src 
    main
        java
            com
                companyname                 --公司简称
                    projectname             --项目简称
                        common              --公用包
                            advice          --统一处理controller
                            annotation      --自定义注解
                            constant        --自定义常量
                            core            --核心包
                            exception       --自定义异常
                            utils           --工具包
                            verify          --验证包
                        configure           --配置文件类
                        controller          --控制类
                            rest            --返回json数据的post,get请求
                            router          --路由接口，get请求
                        mapper              --mybatisMapper类
                        model               --实体类
                        service             --服务类
```
ORM模块相关
1. mapper目录中存放所有Mapper类统一管理;resources/mapper目录下存放所有xml文件。
2. 需要注解```@Mapper```用于spring扫描。
3. 需要集成```CoreMapper```，里面提供了一些基础的方法。
4. ```mybatis.configuration.map-underscore-to-camel-case```默认开启，省去```<ResultMap>```对应，如有非驼峰式的情况，加```Column```注解。
5. 默认用注解方式写sql，简单sql建议使用注解的方式书写便于查找。复杂sql建议采用xml形式书写，便于维护。

参数加密相关
1. ```application-@env@.properties```中```verify.security```代表是否开启参数加密，属于总控制开关，默认关闭。
2. ```verify.signSecret```为秘钥值，可与客户端协商定义。默认为：@Duia^_^(8dJKd80jau。
3. 开启加密后，将会拦截所有被```@RestController```注解的类下的所有```@PostMapping，@GetMapping，@PutMapping，@DeleteMapping，@RequestMapping```注解的方法。
4. 开启加密后，将会拦截所有被```@Controller```注解的类下的所有```@ResponseBody```注释的方法。
5. 当```verify.security```为true时，```@IgnoreValidation```注解在方法上有白名单的效果，用于实现某些方法不受参数验证控制。
6. 当```verify.security```为false时，```@MustValidation```注解在方法上有白名单的效果，用于实现某些方法受参数验证控制。
7. 加密规则是所有非```signature```参数按照首字母默认排序后```param1=value1&param2=value2.....```衔接后直接拼接```密钥值```得到的字符串做```MD5```，再和```signature```参数的值做```equals```。

统一json返回值相关
1. json类型的返回值统一为```Result```类型，由```state，message，data```3个参数组成，其中```state```代表状态码，```message```代表返回信息，```data```则为具体返回数据体。
2. ```ResponseEntity```类负责操纵```Result```类并提供了几个默认的属性值，如```ResponseEntity.OK，ResponseEntity.FAILURE，ResponseEntity.VALIDATION_FAILURE```等等。
3. 如果默认无法满足需求，自定义操作由```ResponseEntity.READY```触发，后续衔接```ResponseEntity.READY.setState(xxx).setMessage(xxxx).setData(xxxx)```。

统一异常处理相关
1. 异常处理采用通知的方式，由```JsonControllerAdvice，WebControllerAdvice```分别对json返回值和页面跳转进行异常处理。


接口文档相关
1.请求地址为/swagger-ui.html
2.默认测试和预发部开启swagger2,生成环境关闭
3.后续如果使用swagger接口文档,在新增接口时维护版本号

全局参数相关
1.ParamsConfiguration中配置application级别的共享参数
2.JsonControllerAdvice中@ModelAttribute,也可使用@sessionAttribute

测试用例相关
1. 为了养成良好的编程习惯，最好进行测试用例的编写，统一在test文件目录下管理。
2. 直接在测试类进行```@RunWith(SpringRunner.class)，@SpringBootTest，@Transactional(可选)```注解即可。
3. ```Mapper```层和```Service层```需要分别进行测试用例的编写。

**活动相关地址**

- 成绩查询预约地址
`http://menglish.static.duia.com/view/reservationQuery.html`

- 成绩查询地址
`http://menglish.static.duia.com/view/queryPerformance.html`

- 优惠券地址
`http://menglish.static.duia.com/view/coupon.html`





    