# HospitalOutpatienManagementSystem
1  详细设计

1.1  系统首页

  进入系统后，医院工作人员和患者根据需要，在如图1.1所示界面选择不同的功能，患者点击“自助服务”按钮自助完成相关事宜，医院工作人员点击“用户登录”按钮，完成登录后，可以开始利用此系统完成工作。
 ![image](https://user-images.githubusercontent.com/64386806/163977940-08e63658-f7ab-4ce3-b8d2-e5cbfdbe54a2.png)

                           图1.1系统首页图
                           
 1.2  自助服务
 
  患者使用该系统时，在如图1.1所示的系统首页界面中，点击“自助服务”按钮进入自助服务功能模块首页，在如图1.2所示。点击左侧任意一个导航窗格，会显示对应窗格包含的功能按钮。主要有“在线按钮”、“查看挂号信息”、“在线缴费”、“在线充值”、“退费/注销”。以下将针对不同功能进行详细介绍。

 ![image](https://user-images.githubusercontent.com/64386806/163978357-fade14bf-ec80-4157-ab70-7c414d68d9e3.png)

                           图1.2 自助服务首页

1.2.1  在线挂号

  在图1.2所示界面中点击“挂号服务”，选择“在线挂号”按钮，进入在线挂号功能，如图1.3所示。患者填写身份证号码与姓名信息后，当输入框失去焦点时，系统会获取到输入框的值，如果说该值是一个空字符串，系统会给出错误提示信息，如图1.4所示。另外，系统利用正则表达式对输入的身份证号码进行校验，如果校验不通过，系统同样会给出错误提示信息。信息填写完成后，点击“立即挂号”按钮，页面会将所有的信息，通过请求发送到控制层，控制层将其封装到Patient类型的对象patient中。然后，patient的status和balance属性分别赋值0和0.00，并以当前系统时间作为其挂号时间。调用service层的insert执行插入操作。执行插入操作之前，系统首先会根据身份证号判断该挂号信息是否已经存在。若存在，返回错误信息。不存在，根据patient的dpetId属性查询出当前科室的挂号总认识count，在对count执行加1操作，并将其赋值给patient的patienetNo属性。最终，调用持久层insert方法将完整的挂号信息插入到挂号信息表。完成后，向页面返回，挂号结果，如图1.5所示。当点击“去充值”按钮的时候，系统会向controller层发送页面跳转请求，将页面切换成充值的页面。
 ![image](https://user-images.githubusercontent.com/64386806/163978722-9fda90fb-97eb-4f97-bd55-271e172cee2e.png)

                           图1.3 在线挂号功能界面图


 ![image](https://user-images.githubusercontent.com/64386806/163978844-5fe09fd4-29d3-4990-b142-112b8a784652.png)

                          图1.4 数据校验结果图

 ![image](https://user-images.githubusercontent.com/64386806/163978890-92ca1f99-2ba1-4d72-b491-be4d2a6c1ba9.png)

                         图1.5 挂号成功界面图
                         
1.2.2  查看挂号信息

  在图1.2所示界面内点击“挂号服务”，选择“查看挂号信息”按钮，进入图1.6所示界面。在输入框内输入想要查询的患者的身份证号码，点击“查询”按钮，系统会自动获取输入框中内容，在后台与数据库patient表中的id_card字段进行比对，若比对结果一致，则会将patient表与department表进行联查，查询出详细的患者挂号信息，显示到页面上，供患者参考。若对比后该患者信息不存在或者患者填写的身份证号为空，会在页面提示“您查询的信息不存在，请检查您填写的身份证号码是否无误”的字样。具体查询结果，如图1.7、图1.8所示。
 ![image](https://user-images.githubusercontent.com/64386806/163979528-42bf0908-fdad-4e78-a090-89c9169f9670.png)

                           图1.6查看挂号信息界面图

 ![image](https://user-images.githubusercontent.com/64386806/163979680-8d781cd3-14d1-4168-b1d2-52cb39d94c01.png)

                           图1.7 查询信息失败界面图


 ![image](https://user-images.githubusercontent.com/64386806/163979726-2c49ad8b-5bdb-4f11-9974-02da83297f75.png)

                           图1.8 查询成功界面图
                           
1.2.3  在线缴费

  进入自助服务首页点击“账单结算”，选择“在线缴费”，进入如图1.9所示界面。患者进入后，在当前页面输入正确身份证号码后，点击“查询”按钮，系统会自动在数据库中的药方表（prescription表）中搜索，表中patient_id字段与输入的身份证号相同的数据，并且会在所有结果中根据prescription表中的status字段，筛选出还没有完成支付的记录，即status字段值为“0”。最后将所有数据采用分页的方式，存储到List集合当中返回到页面进行展示，如图1.10所示。在图1.10中，每条记录左侧会有一个单选框，左上角的是全选框，选中该全选框，会选中当前页面内所有的数据。表格左下角，会记录当前选中的所有药方的价格总和。若患者已经选择好结算的药品，核对好金额后，点击“支付”按钮，如果患者并没有选择结算的药品，则系统会提示选择结算的药品，如果已经选择，会提示是否确定完成支付，点击“确定”，完成支付。确定支付之后，系统会将当前需要支付的费用与数据库中patient表的当前患者的balance字段的值进行比较。如果前者大于后者，则支付失败，系统会提示余额不足，患者需前往完成余额充值。
反之，系统会在当前账户的余额的基础上减去当前支付的费用，并提示支付成功，并修改prescription表中对应字段的status字段值为“1”。支付成功后，系统会再次向后台发出请求查询出该患者的所有未支付的药方信息，用来完成页面的刷新。如图1.11所示。
 
![image](https://user-images.githubusercontent.com/64386806/163980385-f41d0acd-3583-4619-a9da-a6279b5f9539.png)

                          图1.9 在线缴费功能界面

 ![image](https://user-images.githubusercontent.com/64386806/163980425-ed5e86a9-193b-4060-ba29-32a8b7b4b38a.png)

                          图1.10 查询待支付药方结果图

 ![image](https://user-images.githubusercontent.com/64386806/163980466-be3cfbaf-e00c-4f18-aaa7-3f1e803c7faa.png)

                          图1.11 支付成功页面图

1.2.4  在线充值

  点击“账户管理”，选择“在线充值”操作，进入功能页面，如图1.12所示。在此功能中，患者需填写身份证号码和想要充值的金额。同时，对于充值金额该系统设置了患者填写的金额数只能为正整数，并且要小于10000元的限制条件。如果说患者填写的金额格式不符合标准，系统会弹出“请正确填写金额格式，输入的金额只能为正整数”的字样提示。上述两种信息填写无误后，点击“充值”按钮，系统会在数据库的patient表中查询与之匹配的记录，与用户充值的金额一起显示在页面上，如图1.13所示。在图1.13所示界面上，患者可以核实自己的个人信息及充值金额是否正确。确认无误后，点击“立即充值”按钮，完成充值。充值成功后，系统会提示充值成功，同时系统会再次查询当前患者的个人信息包括账户余额情况，展示到页面上，供患者检查充值是否准确到账，如图1.14所示。
 ![image](https://user-images.githubusercontent.com/64386806/163980601-82c8e240-7410-4ca4-a37e-462550cc7a38.png)

                          图1.12 在线充值功能页面图

 ![image](https://user-images.githubusercontent.com/64386806/163980658-89d7c1cc-8407-48ec-ab2f-1a3a447bf13c.png)

                          图1.13 显示充值信息界面图

 ![image](https://user-images.githubusercontent.com/64386806/163980685-9c8e19e6-642e-46d4-a6a1-475ace70d00d.png)

                          图1.14 充值成功界面图

1.2.5  退费/注销

  点击“账户管理”，选择“退费/注销”功能，进入退费/注销功能页面，如图1.15所示。首先，患者需要填写自己的身份证号码，点击“查询”按钮，如果输入的数据为空，将会提示输入身份证号，若输入正确无误系统会在数据库中检索当前患者的挂号信息包括姓名、就诊科室、账户余额及是否已经就诊等并打印在页面上，如图1.16所示。患者检查自己的信息正确后，点击“退费/注销”按钮，首先系统会根据身份证号码，在数据库患者信息表（patient表）中进行查询，找到对应的记录，检查该患者是否已经就诊完毕，即status字段值是否等于1，如果该患者还未就诊则直接在该表中将该条记录删除，并提示账户删除成功与否；如果该患者已经就诊完成，那么就根据该患者的身份证号到药方信息表（prescription表）中，查询该患者的所有药方，检测是否其所有的药方都已经支付完毕，即status字段值是否为1，如果都已经支付完毕，则在患者信息表中执行删除操作，注销其挂号记录。最终，系统给出注销成功的提示“账户注销成功，请取走您的现金”。
 ![image](https://user-images.githubusercontent.com/64386806/163980734-e443f5e1-c077-4c18-8396-b2e41a497253.png)

                          图1.15 退费/注销功能界面图

 ![image](https://user-images.githubusercontent.com/64386806/163980750-c17afe32-820c-43a4-b8d6-31d70babda8a.png)

                          图1.16 退费/注销信息显示界面图

1.2.6  退出系统

  患者结束访问之后，点击图1.2所示页面右上角的“退出”选项，系统会向后台发送页面跳转请求，将页面跳转到图1.1所示的系统首页。

1.3  用户登录

  为了更好地满足于医院门诊的日常工作需求，本系统将该系统的用户分为大体分为普通用户和医生两种，普通用户又分为管理员、库房管理员和药房护士三种。在设计数据库时，利用职位信息表（position表）存储各职位的详细信息，其中该表中的user_category字段是区分各职位信息的关键因素。用户进入如图1.1所示的系统首页后，点击“用户登录”按钮，进入如图1.17所示的登录页面，用户输入工号、密码后，点击“登录系统”按钮，此时，如果用户写入的两种信息二者有一个为空字符串，则会显示“用户名和密码都不能为空”的错误提示，如图1.17所示。若登录信息均已填写，那么该系统将发送登录请求，会在普通用户信息表（user表）和医生信息表（doctor表）中根据用户填写的工号和密码进行查询，并将查询结果封装成对应的实体类。如果该实体类为空，则向页面返回0。否则系统会将把该实体类存放到session域中，同时若该实体类是User类型，返回页面的值将会是该实体类的职位类型编号，若其是Doctor类型，返回值默认为4。最终，系统将会根据返回值的不同，将不同的用户界面准确的展示给其对应的用户。

![image](https://user-images.githubusercontent.com/64386806/163981965-ce14bda7-c0d0-44ac-a1d4-da7e8c09c499.png)

                          图1.17 用户登录界面图

1.4  管理员功能模块

1.4.1  用户添加

  管理员登录系统后，点击左侧导航窗格的“普通用户管理”选项，选择“用户添加”，进入图1.18所示界面。页面刷新完毕后，系统会在数据库职位信息表（position表）中查询出所有的数据并将其封装成多个Position实体类，保存在List集合中。然后，返回到页面与select标签代码拼接到一起，作为下拉菜单显示各职位名称。其次，对于入职时间的输入框，本系统采用的是绑定一个日历组件来规范用户输入的日期信息格式的一致性。员工编号和初始密码两个输入框，在失去焦点的时候，页面会获取当前输入框中的值，如果值为空字符串，页面会给出错误提示。管理员点击“添加”按钮时，页面首先会捕获页面所有组件的value值，有一个为空字符串，将会提示“请完整填写注册信息”。若信息均以填写，系统开始执行添加用户请求。在执行的过程中，首先系统会在普通用户信息表（user表）中查询，以待添加的用户的编号为主键的记录是否存在。如果存在，添加失败，返回用户已存在。否则将数据直接插入到user表。
 ![image](https://user-images.githubusercontent.com/64386806/163982015-36e1090f-f64c-49c9-a7c2-9694d94261d5.png)

                          图1.18 用户添加界面图
1.4.2  查看用户信息

  点击“查看用户信息”按钮，进入图1.19所示界面。本功能的主要目的是可以让管理员根据自己实际的需求查找数据。管理员在进行搜索时可以输入查询条件，系统会将这些条件，加入到后台数据查询逻辑中。做数据查询的时候SQL语句会采用分页查询和模糊查询。总体来说，本次查询共分两步进行：第一步，根据管理员的需求，查询出符合条件见的记录总条数count；第二步就是查询出具体的符合条件的记录，该记录是由普通用户表（user表）与职位信息表（position表）联合查询得出的，最后的记录被封装成UserVo的实体类，这些实体类将被保存到List集合list中。list与count将会作为属性存到PageListVo类型的对象中，最后系统向页面返回PageListVo对象，与准备好的分页插件结合，实现完整的用户信息分页展示功能。
  
  点击“修改”按钮，页面会获取该单选框中存放的userId值，然后在数据库中查询对应的数据，将数据填充到弹出的模态框中，如图1.20所示，管理员修改需要变更的信息后，点击“更新”按钮，系统会选择性的更新user表中对应记录的部分字段值。
  
  点击“删除”按钮，获取到当前页面中所有选中的单选框所代表的userId值，以数组的形式通过url传入到后台，user表中只需删除以该数组中任意元素为主键的记录，即可实现用户信息的批量删除。
  
 ![image](https://user-images.githubusercontent.com/64386806/163982052-33d9b592-9229-48f5-a4cf-d78be441b188.png)

                          图1.19 查看用户信息界面图

 ![image](https://user-images.githubusercontent.com/64386806/163982077-60504ff6-f431-470d-a634-a44c190c064d.png)

                          图1.20 修改普通用户信息界面图

1.4.3  医生添加

  点击“医生管理”，选择“医生添加”选项，进入如图1.21所示页面。与用户添加功能类似，该页面中的医生编号和初始密码框，同样添加了空字符串监测功能，若管理输入的信息等同于空字符串，页面将会给出相应提示；其次，入职日期同样也是添加了用于规范输入格式的日历插件。页面加载的同时，系统会向后台发出ajax请求，查出所有的科室信息和职称信息。然后，将二者分别封装到两个List集合中，两个List集合将存储到Map集合一起返回到页面，用于生成下拉菜单。点击“添加”按钮，管理员填写的信息将以参数的形式到达后台，在后台系统会把所有信息封装成Doctor实体类，参与到SQL语句中。添加时，首先会根据该实体类的doctorId属性判断该医生信息是否已经存在，如果不存在，执行添加操作，将信息插入到医生信息表中；如果存在，添加失败，直接提示该医生信息已存在。
 ![image](https://user-images.githubusercontent.com/64386806/163982113-a148da37-ea2c-4cb0-8a99-a1d195796c6f.png)

                          图1.21 添加医生功能页面图

1.4.4  查看医生信息

  选择图1.21所示页面中的“查看医生信息”选项，进入图1.22所示页面。页面加载的同时系统会查询出所有科室信息返回到页面，做下拉菜单。点击“查询”按钮系统会获取到页面中填写的查询信息，随着请求传递到后台。mapper.xml文件中的查询语句将根据这些查询信息进行模糊查询与条件查询，筛选出符合要求的数据，封装到集合中返回到页面，配合分页插件进行展示。

  管理员选中想要修改的记录，点击“修改”按钮，进入如图1.23所示页面。管理员修改信息后，点击“更新”按钮，首先系统会判断管理员是否修改了该医生的编号。如果修改了，会查询以新编号为主键的医生信息是否已经存在，如果存在，添加失败。其余情况，直接执行update语句修改当前医生的信息。

  管理员选中一条或多条记录，点击“修改”按钮，系统会将选中的所有单选框的value值在后台封装成数组，SQL语句中直接删除所有以数组中任意元素为主键的记录即可。
 ![image](https://user-images.githubusercontent.com/64386806/163982157-87937a86-5b3e-4d51-9e52-9e100ed5d053.png)

                          图1.22 查看医生信息界面图

 ![image](https://user-images.githubusercontent.com/64386806/163982190-5912562a-3557-4363-9d90-6e33cdee43b0.png)

                          图1.23 修改医生信息界面图

                    
1.5  库房管理员功能模块

1.5.1  药品信息录入

  库房管理员进入系统后，点击“药品信息录入”选项卡，进入信息录入页面，如图1.24所示。在该页面上，库管员完整输入需要录入的信息后，点击“提交”按钮，系统会获取当前页面上的数据，同时发送请求到后台控制层，控制层将上述数据封装成Drug实体类，然后从session域中取出当前登录系统的用户，将该用户的姓名作为实体类的operateBy属性的值，随后获取到当前系统的时间作为该药品的入库时间。控制层将该实体类通过传参的方式传到service层，service层首先会判断该实体类的drugId在药品信息表中是否已经存在。如果存在，返回错误信息，否则调用持久层insert方法将该条数据插入到药品信息表中。

![image](https://user-images.githubusercontent.com/64386806/163983388-a8c2fa01-79ba-437e-ad9c-9fce44f70ad6.png)


                          图1.24 药品信息录入界面图

1.5.2  药品订购

  选择“药品订购”选项，进入如图1.25所示界面。库管员完整填写订购信息后，点击“提交订单”按钮，系统将订单信息发送到控制层，控制层将其封装成Order实体类order并完成order的初始化工作。主要包括：系统利用UUIDUtil工具类随机生成一串字符串作为该订单的orderId属性；设置该订单状态为未处理，即oredr的orderStatus属性值为0；获取系统当前时间作为下单时间；在session域中获取到当前库管员的名称作为order的orderBy属性值。之后，将通过service层调用持久层的insert方法，将该条订单信息加入到订单信息表中。

  在图1.25所示界面中点击“待处理订单”选项卡，进入如图1.26所示界面。此功能中，系统首先会查出符合库管员要求的、同时未处理的，即order_status字段值为0订单信息进行显示。点击“完成”按钮，库管员需完善当前药品的信息，如图1.27所示。信息完善结束后，点击“入库”按钮，系统会根据完善的信息以及该条订单包含的药品信息，重新初始化一个Drug对象drug，然后将该药品信息写入数据库表中，如果写入成功，系统会将数据库中该条订单的状态修改为已完成，即order_status字段值为1。否则返回错误信息。点击图1.26中的“删除”按钮，系统将根据该订单的编号，从order表中删除该记录。图1.25所示界面中，点击“历史订单”选项卡，进入历史订单功能页面，如图1.28所示。系统会根据用户需求在订单信息表（orderDetail表）中查询出符合条件并且订单状态为已处理，即order_detail字段值为1的记录，显示在页面上。点击该页面上的“删除”按钮，系统会根据订单编号，在orderDetail表中删除该条记录。

![image](https://user-images.githubusercontent.com/64386806/163983406-bb089a21-2c56-4300-b6de-6ddd25083dbd.png)

                          图1.25 药品订购界面图

 ![image](https://user-images.githubusercontent.com/64386806/163983435-67b98cb8-7ab3-43e0-b4fe-725b27ce5117.png)

                          图1.26 待处理订单界面图
![image](https://user-images.githubusercontent.com/64386806/163983456-6345b0b9-ee0a-4f8d-9b85-f5f45c62d838.png)

 
                          图1.27 完善药品信息界面图

 ![image](https://user-images.githubusercontent.com/64386806/163983473-ab2732a0-e44a-479a-bbb4-8d44b6fc5018.png)

                          图1.28 历史订单界面图

1.5.3  查看药品信息

  点击“查询药品信息”选项，进入如图1.29所示界面。系统会根据库管员的不同需求从药品信息表（drug表）中，利用SQL语句的模糊查询，查找出符合条件的数据及记录总条数，然后将二者封装到PageListVo类的对象中，返回到页面进行分页显示。
  
  点击页面中“加购”按钮，库管员需输入加购的数量，如图1.30所示。使用正则表达式检验输入的数量是否为正整数。点击“立即下单”按钮，系统会在控制层创建一个订单实体类orderDetail，并根据选中的药品信息对orderDetail进行初始化。初始化结束之后，调用OrderService类的addOrder方法，向订单信息表中添加该数据。最后返回，结果信息。
  
  点击“退货”按钮，库管员可以根据情况填写备注信息，如图1.31所示。首先，系统会创建一个出库记录类型的对象outRecord。然后，根据待退货的药品信息以及备注信息给ourRecord的属性赋值。完成后执行OutRecordService的addRecord方法。在该方法中系统首先会执OutRecordMapper类的insert方法，如果说该插入方法执行失败，返回错误信息。否则，会对drug表执行删除操作，删除表中的药品信息。
 ![image](https://user-images.githubusercontent.com/64386806/163983491-969c1b1c-f34f-4dd1-b6a1-e51dc8c49306.png)

                          图1.29 查看药品信息界面图
 ![image](https://user-images.githubusercontent.com/64386806/163983520-b59c7f14-c84d-4f4e-9ad0-50390445f2a0.png)

                          图1.30 加购药品界面图
 ![image](https://user-images.githubusercontent.com/64386806/163983549-ce1ef2db-681f-4e8f-ab23-926e5af31b2c.png)

                          图1.31 退货界面图
                          
1.5.4  药品出库记录查询

  选择“药品出库记录”选项卡，进入如图1.32所示界面。点击“搜索”按钮，系统会获取到表单中填写的内容，控制层负责将这些内容封装到出库记录实体类outRecord中，并将其与分页信息存储到map集合，参与到查询操作当中。service层首先会根据信息查出符合条件的记录总条数，然后查出具体出库记录信息，二者存储在PageListVo实体类中被返回到页面。
  
  点击“删除”按钮，系统会获取到当前出库记录的recordId值，然后在service层以该值作为删除条件调用持久层的删除方法，完成记录的删除。
 ![image](https://user-images.githubusercontent.com/64386806/163983576-fa400e02-498f-49a7-8b30-211acb441ea1.png)

                          图1.32 药品出库记录界面图

1.5.5  药品异常监测

  选择“药品异常监测”选项，进入如图1.33所示页面。页面所示条件共分为过期和余量不足两种。
  
  如果查询过期的药品，系统在控制层会获取当前系统的日期时间，并将其作为查询条件，在service层调用持久层的selectByDate方法查询出所有有效期限小于系统当前时间的药品。
  
  如果查询余量不足的药品，首先要输入一个整数值，如图1.34所示，系统在service层调用持久层的selectByStock方法,在药品信息表中查询出所有drug_stock字段值小于库管员输入的值得所有药品信息。点击“加购”按钮，库管员可以加购该药品，如图1.35所示。点击“订购”按钮后，控制层会以当前加购的药品信息和加购信息为依据，将上述信息封装到OrderDetail类型对象orderDetail中，同时完善orderDetail中包括订单状态、操作员在内的信息。然后调用service层的addOrder方法来执行OrderMapper接口中的insert方法，按照orderDetail中的数据，将该条订单信息插入到订单信息表中。
 ![image](https://user-images.githubusercontent.com/64386806/163983600-2454170f-c738-4566-a2b3-43b7169a56dd.png)

                          图1.33 查询过期药品界面图
 ![image](https://user-images.githubusercontent.com/64386806/163983622-c4d192c8-14c0-4248-ba1e-ffd4e47413da.png)

                          图1.34 查询余量不足药品界面图
 ![image](https://user-images.githubusercontent.com/64386806/163983634-24c45c43-db1e-4c3d-a13b-4cbae9bd145d.png)

                          图1.35 余量不足药品加购界面图
                          
 1.6  药房护士功能模块
 
1.6.1  取药

  药房护士登录系统后，选择“取药”功能，输入患者的身份证号码后，点击“查询”按钮，系统会在后台的代码中调用持久层的selectByPrimaryKeyPageList方法，查询出该患者所有药方中已经支付但未取走的药品，即以该身份证号码为主键，status字段值为1，out_status字段值为0的记录。将所有记录以分页的方式显示在页面。具体信息如图1.36所示。
  
  点击图1.36中的“取药”按钮，首先系统会调用DrugService类的updateStock方法，检查库存量是否大于等于取药的数量。如果大于等于，根据该药品的drugId在药品信息表中将该药品的库存量修改为库存量减去取药数量的值。否则返回错误信息。库存量更新完毕后，控制层将新建一个OutRecord类型的实体类outRecord，根据取药信息对outRecord初始化，然后插入到出库记录表中。上述两步骤成功执行后，将药方信息表中该药方的取药状态更新为已取药，即out_status字段值改为1。

![image](https://user-images.githubusercontent.com/64386806/163984553-f7a8863e-f8c8-4620-906f-704e176cd9bf.png)

                          图1.36 取药界面图

1.6.2  打印药方信息

  选择“打印药方信息”功能，输入患者身份证号码，点击“打印”按钮即可。此功能实现大致分三部分。第一部分：根据患者身份证号码，在用患者信息表和科室信息表进行联查，查询结果保存在PatientVo的实体类中。如果该实体类为null,返回错误信息，否则执行第二部分；第二部分：查询该患者是否存在未支付或未取走的药品，即prescription表中所有patientId等于该身份证号的记录的status和out_status字段值是否均为1，如果均为1，执行第三部分，否则返回错误信息；第三部分：根据身份证号码在病历表（medicalRecord表）中查询该患者的诊断结果。上述步骤执行完毕后，系统会将所有信息封装到Map集合中，展示到页面，如图1.37所示。

![image](https://user-images.githubusercontent.com/64386806/163984596-c1cb5c54-3122-46c0-8ff8-ba69a39edd2a.png)

                          图1.37 打印药方信息界面图

1.7  医生功能界面

  医生登录系统后，控制层会首先在session域中拿到该医生的信息，调用PatientMapper类中的getNoseePatient方法，该方法主要是根据医生所属的科室编号查询到该科室所有的患者，然后筛选出未就诊的患者，即status字段值为0，根据每个患者的就诊号在SQL语句中进行升序排序，再用limit关键字限制只查询一条记录，最终获得当前应该就诊的患者并将其信息填充到页面上。如图1.38所示。
  
  医生填写病例后点击“保存病历”按钮，控制层将病历信息及部分患者信息封装成MedicalRecord类型对象medicalRecord，并根据session域中存储的医生与该患者的详细信息，来完善medicalRecord的属性值。最后调用持久层的insert方法，在SQL语句中根据该对象的属性值，将病例记录保存到病历信息表（medicalRecord表）中。
  
  点击“选择药品”按钮，进入图1.39所示界面。医生通过查询可以检索出对应的药品，点击该药品后面的“添加”按钮，系统会将该药品信息在控制层封装成药方的实体类prescription，默认数量为1，总价为数量与药品单价的乘积。然后，根据session中的doctor信息将prescription补充完整。之后通过持久层将该药方写入药方信息表。模态框关闭后，系统会将该患者的药方信息查询出来显示在页面上，如图1.40所示。医生可以设置每种药品需要的数量，点击“添加”按钮，进入如图1.41所示界面。写完医嘱后，点击“完成”按钮，系统会将当前患者的身份证号、药品编号、医嘱信息等存储在药方的实体类中，调用持久层的updateByPrimaryKeySelective方法，将该条药方的药品数量、总价变更为药方实体类当中的信息，同时将该条药方的医嘱信息存储进去。
  
  点击图1.38所示页面中的“下一位”按钮，首先系统会从session域中获取到当前患者的idCard属性值，并根据该值调用service层updateStatus方法更改该患者的就诊状态为已就诊即status字段值改为1。修改完成后再次调用该Controller中的getPatient方法获取下一个就诊患者。
 ![image](https://user-images.githubusercontent.com/64386806/163984713-af49c6fd-0f51-4679-8d21-7b5c7bab52e9.png)

                          图1.38 医生看病界面图

 ![image](https://user-images.githubusercontent.com/64386806/163984741-fabc00b8-f589-4098-a2f8-55781b1a86ad.png)

                          图1.39 医生开药界面图
 ![image](https://user-images.githubusercontent.com/64386806/163984767-5989b280-8601-4048-94e2-cbd6278f7ea8.png)

                          图1.40 药方信息展示界面图

 ![image](https://user-images.githubusercontent.com/64386806/163984780-fd13cc75-82d2-4aa4-9a8d-80ba9943f069.png)

                          图1.41 医嘱信息填写界面

1.8  编辑资料

  用户登录系统后，点击“设置”，选择“编辑资料”，进入如图1.42所示界面。用户修改自己的信息后点击修改按钮，系统捕获页面内可以修改的输入框中的内容，发送请求到控制层，将捕获到的内容封装到User类对象user中。从session域中取出当前登录用户的userId赋值给user。然后调用持久层updateByPrimaryKeySelective方法，按照user中的数据，在SQL语句中采用条件查询，更新当前用户的个人信息。
    
  点击“修改密码”按钮，进入图1.43所示界面。点击“确认修改”按钮，系统首先会对写入的信息进行校验，通过后系统会将当前用户的userId和新密码作为参数，调用持久层changePassword方法将当前用户的密码修改为新密码。修改成功后，页面会执行window.location.href=”/loginIndex”代码将页面跳转到登录页，并在跳转时使用拦截器清除掉session域中原来的用户信息。
 
 ![image](https://user-images.githubusercontent.com/64386806/163984807-15dc77f8-b5da-4a07-baab-21a277bf8230.png)

                          图1.42 编辑资料界面图
 ![image](https://user-images.githubusercontent.com/64386806/163984836-8c3bb086-87d2-4776-95ad-758e07d91163.png)

                          图1.43 修改密码界面图




