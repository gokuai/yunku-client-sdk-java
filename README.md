/*
Title:够快云库 Client SDK 使用说明
Description:
Author: Brandon
Date: 2016/10/12
Robots: noindex,nofollow
*/

# 够快云库3.0 Client SDK 使用说明

[![](https://jitpack.io/v/gokuai/yunku-client-sdk-java.svg)](https://jitpack.io/#gokuai/yunku-client-sdk-java)

* 版本：3.0
* 创建：2016-10-12

## 引用
将`[yunku-client-sdk-java].jar`文件引用进项目，或者将`YunkuAPILibrary`做为依赖项目。

或者Gradle :

```groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

```groovy
	dependencies {
	        compile 'com.github.gokuai.yunku-client-sdk-java:YunkuAPILibrary:3.0'
	}
```
或者Maven：

```xml
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```

```xml
	<dependency>
	    <groupId>com.github.gokuai.yunku-sdk-java</groupId>
	    <artifactId>YunkuAPILibrary</artifactId>
	    <version>3.0</version>
	</dependency>
```

## 初始化

要使用云库3.0的API，您需要先在 <a href="http://developer.gokuai.com/yk/tutorial#yk3" target="_blank">企业授权</a> 中获取 `client_id` 和 `client_secret`

## 参数使用

以下使用到的方法中，如果是string类型的非必要参数，如果是不传，则传`null`

# **云库API（YKHttpEngine.java）**

### 构造方法
	new YKHttpEngine（ClientId, ClientSecret）

#### 参数 
| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| ClientId | 是 | string | 申请应用时分配的AppKey |
| ClientSecret | 是 | string | 申请应用时分配的AppSecret |

---

### 账号密码登录
	loginSync(String account, String password)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| account | 是 | string | 账号 |
| password | 是 | string | 密码 |

#### 返回结果

    {
        "expires_in":Access Token的有效期，以秒为单位,
        "access_token":用于调用access_token，接口获取授权后的access token,
        "refresh_token":用于刷新access_token 的 refresh_token，有效期7天,
        "device_count":设备唯一号,
        "dateline":时间戳
    }

---

## **账号API**

### 获取用户信息
	getAccountInfo()
	
#### 参数

(无)

#### 返回结果
    {
        uuid:
        member_id: 用户编号
        member_email: 用户邮箱
        member_name: 用户名
        member_phone: 手机号码
        member_account: 
        avatar: 头像地址
        language: 语音
        validate: 
        settings: 个人设置,返回所有存储在 gk_member_auth 中的信息
        {
            favorite_names: 收藏夹各种星标的用户自定义名称列表
        },
        favorite_names:{
            "1":"星形",
            "2":"月亮",
            ...
        },
        isvip: 是否VIP用户
        product_id: 产品ID
        product_name: 产品名称
        yunku_count:
        oauths:[
            "dingding",
            "email"
        ],
        property:{
            "edit_password" : "1" //强制修改密码
            ...
        }
    }

---

### 注册
	registerAsync(final DataListener listener, final String name, final String email, final String password)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| listener | 是 | DataListener | 从服务器获得数据后的回调 |
| name | 是 | string | 用户名 |
| email | 是 | string | 邮箱 |
| password | 是 | string | 密码 |

#### 返回结果

    {
        member_id:123
    }

---

### 找回密码
	findPassword(DataListener listener, String email)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| listener | 是 | DataListener | 从服务器获得数据后的回调 |
| email | 是 | string | 邮箱 |

#### 返回结果

正常返回 HTTP 200

---

### 找回密码
	findPassword(final DataListener listener, final String email)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| listener | 是 | DataListener | 从服务器获得数据后的回调 |
| email | 是 | string | 邮箱 |

#### 返回结果

正常返回 HTTP 200

---

### 更改用户显示名
	uploadUserName(final DataListener listener, final String name)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| listener | 是 | DataListener | 从服务器获得数据后的回调 |
| name | 是 | string | 用户名称 |

#### 返回结果

{
    member_name:string //用户名称
    avatar_url:string //头像地址
}

---

### 更改用户联系电话
	uploadUserPhone(final DataListener listener, final String mobile)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| listener | 是 | DataListener | 从服务器获得数据后的回调 |
| mobile | 是 | string | 用户联系电话 |

#### 返回结果

{
    member_name:string //用户名称
    avatar_url:string //头像地址
}

---

### 获取库列表
	getMountsInfo()
	
#### 参数

（无）

#### 返回结果

    {'list':
        [
            {
                ent_id:
                org_id,
                org_name,
                member_count,
                subscribe_count,
                org_logo_url,
                org_background,
                org_find,
                member_id,
                member_name,
                member_type,
                mount_id,
                storage_point,
                storage_cache,
                storage_ethernet,
                compare,
                size_org_total,
                size_org_use,
                product_id,
                product_name,
                remain_days,
                auths:{
                    allow_find_org: 1 // 允许发现
                    allow_visit_website: 1 // 允许网页访问
                },
                property:{
                    permissions:["ent_org",...]
                }
                addtime,
                add_dateline
            },
            ...
        ]
    }

| 字段 | 类型 | 说明 |
|---|---|---|
|ent_id|int|企业唯一ID|
|org_id|int|团队唯一ID|
|org_name|string|团队名称|
|member_count|int|成员总数|
|subscribe_count|int|订阅成员总数|
|org_logo_url|string|团队图标地址|
|org_background|string|团队背景|
|org_find|int|团队可发现 0:不可发现,1:可发现|
|member_id|int|用户唯一ID|
|member_name|string|用户名称|
|member_type|int|0：超级管理员 1：普通管理员成 2：普通成员|
|mount_id|int|mount_id|
|storage_point|string|存储点|
|storage_cache|int|是否开启cache|
|storage_ethernet|int|是否允许外网访问|
|compare|int|客户端是否同步数据|
|size_org_total|bigint|团队空间总大小|
|size_org_use|bigint|团队已使用空间大小|
|product_id|int|产品ID|
|product_name|string|产品名称|
|remain_days|int|剩余天数, 到期则为0, -1表示永不到期|
|property|string|json格式字段,用户权限-permissions，是否隐藏-hide|
|addtime|string|加入时间|
|add_dateline|int|加入时间戳|

---

### 获取企业列表
	getEntInfo()
	
#### 参数

(无)

#### 返回结果

    {'list':
        [
        		{
        			ent_id,
        			ent_name,
        			addtime,
        			add_dateline,`
        			roles,
        			trial,
        			is_expired,
        			member_count,
        			modules,
        			property
        		},
        	...
        ]
    }

| 字段 | 类型 | 说明 |
|---|---|---|
|ent_id|int|企业ID|
|ent_name|string|企业名称|
|addtime|string|加入时间|
|add_dateline|int|加入时间戳|
|roles|array|企业角色列表|
|trial|bool|是否试用|
|is_expired|bool|是否过期|
|member_count|int|总人数|
|modules|array|开通的模块|
|property|string|属性 |

roles的数据结构

	[
		{
			id:角色ID(int),
			name:角色名(string)
			admin:是否系统管理员(int),
			org_admin:是否库管理员(int),
			description:描述(string),
			permissions:权限列表(string),
			default_role:是否默认角色(int)
		},
		...
	]

property的数据结构

	{
		ent_admin:用户是否企业管理员,
		ent_super_admin:是否允许管理企业信息
		enable_manage_groups:允许管理的部门(string) #以 "|" 符号分割，如 |0|1|2|
		enable_create_org:是否允许创建库
		enable_publish_notice:是否允许发布公告
	}  
		
---

### 更改设备状态
	changeDeviceState(final DataListener listener, final int deviceId, final int state)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| listener | 是 | DataListener | 从服务器获得数据后的回调 |
| deviceId | 是 | int | 设备ID |
| state | 是 | int | 状态 1/0 |

#### 返回结果

正常返回 HTTP 200

---

### 更改设备名称
	setDeviceInfo(final DataListener listener, final String device, final String info)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| listener | 是 | DataListener | 从服务器获得数据后的回调 |
| device | 是 | string | 设备 |
| info | 是 | string | 设备信息 |

#### 返回结果

正常返回 HTTP 200

---

### 删除设备
	delDevice(final DataListener listener, final int deviceId)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| listener | 是 | DataListener | 从服务器获得数据后的回调 |
| deviceId | 是 | int | 设备ID |

#### 返回结果

正常返回 HTTP 200

---

### 是否禁止新设备登录
	changeNewDeviceState(final DataListener listener, final int state)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| listener | 是 | DataListener | 从服务器获得数据后的回调 |
| state | 是 | int | 状态 1/0 |

#### 返回结果

正常返回 HTTP 200

---

### 获取服务器
	getServerSite(String type)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| type | 是 | string | 服务类型 |

#### 参数type值说明

yunku2.0:

* chat:聊天服务器
* upload:上传服务器
* download: 下载服务器
* doc：Office文档转换服务器
* cad: CAD转换服务器
* notify2: 文件同步通知
* dialog：会话消息服务器
* note: 笔记服务器

yunku3.0:

* upload3: 上传服务器
* download: 下载服务器
* doc：Office文档转换服务器
* cad: CAD转换服务器
* dialog3: 会话消息服务器
* notify3: 文件同步通知
* note3: 笔记服务器
* m-dialog: 会话消息服务器（统一域名用）
* m-notify: 文件同步通知（统一域名用）


#### 返回结果

	[
		{
			host:地址(string) //deprecated,
			hostname:地址(string),
			hostname-in:内网地址(string),
			port:端口(string),
			https:是否支持https, 
				0表示不支持, 
				1表示可以使用443连接, 
				数字表示https端口(string)
			path:路径(string),
			sign:签名key(string),
			dateline:时间戳(int) dialog类型会返回
		},
		...
	]

---

### 获取账号设置
	getAccountSetting()
	
#### 参数

(无)

#### 返回结果

| 名称 | 类型 | 说明 |
| --- | --- | --- |
| list | array | 设置 |

list说明：
	
	[
		{
			setting:设置名称(string),
			value:值(int),
			property:扩展属性(string json)
		},
		...
	]
	
---

## **库API**

### 获取存储点列表
	getStoragePoint(final DataListener listener, final int endId)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| listener | 是 | DataListener | 从服务器获得数据后的回调 |
| endId | 是 | int | 企业ID |

#### 返回结果

	{
		list: [
			{
				id:存储点ID(int),
				storage_point: 存储点(string),
				allocate_capacity: 允许创建库大小(bit), //0表示无限制
				name: 存储点名称(string)
			},
			......
		]
	}

---

### 获取默认库图标列表
	getDefaultLibLogos()
	
#### 参数

（无）

#### 返回结果

	{
		list: [
			{
				url:图标地址(string),
				description: 图标描述(string),
				value: 保存时的图标的值(string)
			},
			......
		]
	}

---

### 获取库信息
	getLibInfo(int mountId, int orgId)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | 库MOUNTID |
| orgId | 是 | int | 库ID |

#### 返回结果

	{
    		org_id: 库ID(int),
    		mount_id: 库MOUNTID(int),
    		ent_id: 企业ID(int),
    		member_id: 创建者ID(int),
    		storage_point: 存储点(string),
    		storage_cahce: 是否开启cache服务器(int),
    		org_name: 库名称(string),
    		org_description: 库描述(string),
    		org_logo: 库图标(string),
    		org_logo_url: 库图标地址(string),
    		size_use: 库已使用空间大小(int),
    		size_total: 库空间大小(int),
    		member_limit: 库成员数上限(int), //-1为无限制
    		member_count: 库成员数(int),
    		group_count: 库部门数(int),
    		add_dataline: 库创建时间戳(string),
    		can_manage: 是否允许修改库信息或管理库(bool),
    		can_quit: 是否允许退出库(bool),
    		can_delete: 是否允许删除库(bool),
    		collection: {
    			show: 是否限制收集库信息(bool),
    			url: 收集库地址(string),
    			state: 收集库状态(bool), //1:开启; 0:关闭
    		},
    		unique_code: 收集库验证码(string),
    		property: 其他属性(string),
    		file_count: 文件数量(int),
            folder_count: 文件夹数量(int)
    	}

---

### 搜索库成员
	searchLibraryMembers(final int orgId, final int start, final String keyWord,
                                           final int size, final boolean withInfo, final boolean withGroup)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| orgId | 是 | int | 库ID |
| start | 否 | int | 开始位置 |
| keyWord | 否 | string | 查询关键词，邮箱,名称或拼音首字母 |
| size | 否 | int | 数量，不传拿全部 |
| withInfo | 否 | boolean | 是否返回成员信息 |
| withGroup | 否 | boolean | 是否返回包含子部门成员 |

#### 返回结果

	{
    		list: [
    			{
    				org_id:库ID(int),
         			mount_id:库MOUNTID(int),
          			ent_id:企业ID(int),
          			member_id:成员ID(int),
          			member_name: 成员名(string),//须with_info参数传1
          			member_letter: 成员首字母(string),//须with_info参数传1
          			member_email: 成员邮箱(string),//须with_info参数传1
          			member_type:成员类型(int),//0:超级管理员;1:管理员;2:成员 企业库请自动忽略
          			role_id:成员库上角色(int),//个人库请自动忽略
          			role_ids: [
          				角色ID(int),//所有角色，须with_info参数传1
          				......
          			],
          			is_stand:是否独立添加(int),//是否被独立添加到库上的，须with_info参数传1
          			state:成员在库上状态(bool),//0:禁用 1:正常
          			addtime:添加时间(timestamp)
    			},
    			......
    		],
    		count: 总数(int)
    	}

---

### 获取成员关系
	getMemberRelative(int orgId, int start, boolean withInfo, boolean withGroup)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| orgId | 是 | int | 库ID |
| start | 否 | int | 开始位置 |
| withInfo | 否 | boolean | 是否返回成员信息 |
| withGroup | 否 | boolean | 是否返回包含子部门成员 |

#### 返回结果

	{
    		list: [
    			{
    				org_id:库ID(int),
         			mount_id:库MOUNTID(int),
          			ent_id:企业ID(int),
          			member_id:成员ID(int),
          			member_name: 成员名(string),//须with_info参数传1
          			member_letter: 成员首字母(string),//须with_info参数传1
          			member_email: 成员邮箱(string),//须with_info参数传1
          			member_type:成员类型(int),//0:超级管理员;1:管理员;2:成员 企业库请自动忽略
          			role_id:成员库上角色(int),//个人库请自动忽略
          			role_ids: [
          				角色ID(int),//所有角色，须with_info参数传1
          				......
          			],
          			is_stand:是否独立添加(int),//是否被独立添加到库上的，须with_info参数传1
          			state:成员在库上状态(bool),//0:禁用 1:正常
          			addtime:添加时间(timestamp)
    			},
    			......
    		],
    		count: 总数(int)
    	}

---

### 获取部门关系
	getGroupRelative(int orgId, boolean withInfo)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| orgId | 是 | int | 库ID |
| withInfo | 否 | boolean | 是否返回部门信息 |

#### 返回结果

	{
    		list: [
    			{
    				org_id:库ID(int),
    				name: 部门名称(string),//需要with_info参数传1
    				count: 部门成员数(int),//需要with_info参数传1
         			mount_id:库MOUNTID(int),
          			ent_id:企业ID(int),
          			group_id:部门ID(int),
          			role_id:部门库上角色(int),//个人库请自动忽略
          			addtime:添加时间(timestamp),
          			path:部门完整路径(string)
    			},
    			......
    		]
    	}

---

### 添加库部门
	addLibraryGroup(final int entId, final int orgId,final int groupId, final int roleId)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| entId | 是 | int | 企业ID |
| orgId | 是 | int | 库ID |
| groupId | 是 | int | 部门ID |
| roleId | 是 | int | 角色ID |

#### 返回结果

	{
    		org_id:库ID(int),
    		mount_id:库MOUNTID(int),
    		ent_id:企业ID(int),
    		group_id:部门ID(int),
    		role_id:部门库上角色(int),
    		addtime:添加时间(timestamp),
    		path:部门完整路径(string)
    	}

---

### 修改库部门
	updateLibraryGroup(final int entId, final int orgId,final int groupId, final int roleId)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| entId | 是 | int | 企业ID |
| orgId | 是 | int | 库ID |
| groupId | 是 | int | 部门ID |
| roleId | 是 | int | 角色ID |

#### 返回结果

	{
		org_id:库ID(int),
		mount_id:库MOUNTID(int),
		ent_id:企业ID(int),
		group_id:部门ID(int),
		role_id:部门库上角色(int),
		addtime:添加时间(timestamp),
		path:部门完整路径(string)
	}

---

### 移除库部门
	delLibraryGroup(final int entId, final int orgId,final int groupId)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| entId | 是 | int | 企业ID |
| orgId | 是 | int | 库ID |
| groupId | 是 | int | 部门ID |

#### 返回结果

正常返回 HTTP 200

---

### 添加库成员
	addLibraryMember(final int orgId, final String memberids,final int roleId)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| orgId | 是 | int | 库ID |
| memberids | 是 | string | 成员ID,以逗号分隔 |
| roleId | 是 | int | 角色ID |

#### 返回结果

	{
		list: [
			{
				org_id:库ID(int),
     			mount_id:库MOUNTID(int),
      			ent_id:企业ID(int),
      			member_id:成员ID(int),
      			member_type:成员类型(int),//0:超级管理员;1:管理员;2:成员 企业库请自动忽略
      			role_id:成员库上角色(int),//个人库请自动忽略
      			state:成员在库上状态(bool),//0:禁用 1:正常
      			addtime:添加时间(timestamp)
			},
			......
		],
		error_list: [
			{
      			member_id:成员ID(int),
      			member_name:成员名称(string),
      			error_code: 错误码(int),
      			error_msg: 错误信息(string)
      		},
			......
		]
	}

---

### 修改库成员角色
	changeLibraryMemberRole(final int orgId, final String memberids, final int roleId)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| orgId | 是 | int | 库ID |
| memberids | 是 | string | 成员ID,以逗号分隔 |
| roleId | 是 | int | 角色ID |

#### 返回结果

    {
		list: [
			{
				org_id:库ID(int),
     			mount_id:库MOUNTID(int),
      			ent_id:企业ID(int),
      			member_id:成员ID(int),
      			member_type:成员类型(int),//0:超级管理员;1:管理员;2:成员 企业库请自动忽略
      			role_id:成员库上角色(int),//个人库请自动忽略
      			state:成员在库上状态(bool),//0:禁用 1:正常
      			addtime:添加时间(timestamp)
			},
			......
		]
	}

---

### 移除库成员
	delLibraryMember(final int orgId, final String memberids)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| orgId | 是 | int | 库ID |
| memberids | 是 | string | 成员ID,以逗号分隔 |

#### 返回结果

	{
		list: [
			{
				id:存储点ID(int),
				storage_point: 存储点(string),
				allocate_capacity: 允许创建库大小(bit), //0表示无限制
				name: 存储点名称(string)
			},
			......
		]
	}

---

### 创建库
	createLibrary(final String name, int entId,final String description, 
	                        final String logo, final String storagePoint, long capacity)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| name | 是 | string | 库名称 |
| entId | 否 | int | 企业ID，个人库则不需要传 |
| description | 否 | string | 库描述 |
| logo | 否 | string | 库的LOGO地址,注:不带http的地址 |
| storagePoint | 否 | string | 存储点，不传或传空则为公有云, 企业库专用 |
| capacity | 否 | long | 库空间, -1表示大小无限, 企业库专用 |

#### 返回结果

	{
    		org_id: 库ID(int),
    		mount_id: 库MOUNTID(int),
    		ent_id: 企业ID(int),
    		member_id: 创建者ID(int),
    		storage_point: 存储点(string),
    		storage_cahce: 是否开启cache服务器(int),
    		org_name: 库名称(string),
    		org_description: 库描述(string),
    		org_logo: 库图标(string),
    		org_logo_url: 库图标地址(string),
    		size_use: 库已使用空间大小(int),
    		size_total: 库空间大小(int),
    		member_limit: 库成员数上限(int), //-1为无限制
    		member_count: 库成员数(int),
    		add_dataline: 库创建时间戳(string),
    		can_manage: 是否允许修改库信息或管理库(bool),
    		can_quit: 是否允许退出库(bool),
    		can_delete: 是否允许删除库(bool),
    		collection: {
    			show: 是否限制收集库信息(bool),
    			url: 收集库地址(string),
    			state: 收集库状态(bool), //1:开启; 0:关闭
    		},
    		unique_code: 收集库验证码(string),
    		property: 其他属性(string)
    	}

---

### 改变团队图标操作
	changeCLoudLogo(final DataListener listener,
                                      final int orgId, final String logoUrl)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| listener | 是 | DataListener | 从服务器获得数据后的回调 |
| orgId | 是 | int | 企业ID |
| logoUrl | 是 | string | 库的LOGO地址,注:不带http的地址 |

#### 返回结果

	{
    		org_id: 库ID(int),
    		mount_id: 库MOUNTID(int),
    		ent_id: 企业ID(int),
    		member_id: 创建者ID(int),
    		storage_point: 存储点(string),
    		storage_cahce: 是否开启cache服务器(int),
    		org_name: 库名称(string),
    		org_description: 库描述(string),
    		org_logo: 库图标(string),
    		org_logo_url: 库图标地址(string),
    		size_use: 库已使用空间大小(int),
    		size_total: 库空间大小(int),
    		member_limit: 库成员数上限(int), //-1为无限制
    		member_count: 库成员数(int),
    		add_dataline: 库创建时间戳(string),
    		can_manage: 是否允许修改库信息或管理库(bool),
    		can_quit: 是否允许退出库(bool),
    		can_delete: 是否允许删除库(bool),
    		collection: {
    			show: 是否限制收集库信息(bool),
    			url: 收集库地址(string),
    			state: 收集库状态(bool), //1:开启; 0:关闭
    		},
    		unique_code: 收集库验证码(string),
    		property: 其他属性(string)
    	}

---

### 改变团队名称操作
	changeOrgName(final DataListener listener,
                                    final int orgId, final String name)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| listener | 是 | DataListener | 从服务器获得数据后的回调 |
| orgId | 是 | int | 企业ID |
| name | 是 | string | 库名称 |

#### 返回结果

	{
    		org_id: 库ID(int),
    		mount_id: 库MOUNTID(int),
    		ent_id: 企业ID(int),
    		member_id: 创建者ID(int),
    		storage_point: 存储点(string),
    		storage_cahce: 是否开启cache服务器(int),
    		org_name: 库名称(string),
    		org_description: 库描述(string),
    		org_logo: 库图标(string),
    		org_logo_url: 库图标地址(string),
    		size_use: 库已使用空间大小(int),
    		size_total: 库空间大小(int),
    		member_limit: 库成员数上限(int), //-1为无限制
    		member_count: 库成员数(int),
    		add_dataline: 库创建时间戳(string),
    		can_manage: 是否允许修改库信息或管理库(bool),
    		can_quit: 是否允许退出库(bool),
    		can_delete: 是否允许删除库(bool),
    		collection: {
    			show: 是否限制收集库信息(bool),
    			url: 收集库地址(string),
    			state: 收集库状态(bool), //1:开启; 0:关闭
    		},
    		unique_code: 收集库验证码(string),
    		property: 其他属性(string)
    	}

---

### 改变团队描述操作
	changeCLoudDesc(final DataListener listener,
                                      final int orgId, final String desc)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| listener | 是 | DataListener | 从服务器获得数据后的回调 |
| orgId | 是 | int | 企业ID |
| desc | 是 | string | 库描述 |

#### 返回结果

	{
    		org_id: 库ID(int),
    		mount_id: 库MOUNTID(int),
    		ent_id: 企业ID(int),
    		member_id: 创建者ID(int),
    		storage_point: 存储点(string),
    		storage_cahce: 是否开启cache服务器(int),
    		org_name: 库名称(string),
    		org_description: 库描述(string),
    		org_logo: 库图标(string),
    		org_logo_url: 库图标地址(string),
    		size_use: 库已使用空间大小(int),
    		size_total: 库空间大小(int),
    		member_limit: 库成员数上限(int), //-1为无限制
    		member_count: 库成员数(int),
    		add_dataline: 库创建时间戳(string),
    		can_manage: 是否允许修改库信息或管理库(bool),
    		can_quit: 是否允许退出库(bool),
    		can_delete: 是否允许删除库(bool),
    		collection: {
    			show: 是否限制收集库信息(bool),
    			url: 收集库地址(string),
    			state: 收集库状态(bool), //1:开启; 0:关闭
    		},
    		unique_code: 收集库验证码(string),
    		property: 其他属性(string)
    	}

---

### 改变团队空间大小操作
	changeCLoudSpace(final DataListener listener,
                                       final int orgId, final int capacity)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| listener | 是 | DataListener | 从服务器获得数据后的回调 |
| orgId | 是 | int | 企业ID |
| capacity | 是 | string | 库空间, -1表示大小无限, 企业库专用 |

#### 返回结果

	{
    		org_id: 库ID(int),
    		mount_id: 库MOUNTID(int),
    		ent_id: 企业ID(int),
    		member_id: 创建者ID(int),
    		storage_point: 存储点(string),
    		storage_cahce: 是否开启cache服务器(int),
    		org_name: 库名称(string),
    		org_description: 库描述(string),
    		org_logo: 库图标(string),
    		org_logo_url: 库图标地址(string),
    		size_use: 库已使用空间大小(int),
    		size_total: 库空间大小(int),
    		member_limit: 库成员数上限(int), //-1为无限制
    		member_count: 库成员数(int),
    		add_dataline: 库创建时间戳(string),
    		can_manage: 是否允许修改库信息或管理库(bool),
    		can_quit: 是否允许退出库(bool),
    		can_delete: 是否允许删除库(bool),
    		collection: {
    			show: 是否限制收集库信息(bool),
    			url: 收集库地址(string),
    			state: 收集库状态(bool), //1:开启; 0:关闭
    		},
    		unique_code: 收集库验证码(string),
    		property: 其他属性(string)
    	}

---

### 删除库
	deleteLib(final DataListener listener, final int orgId)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| listener | 是 | DataListener | 从服务器获得数据后的回调 |
| orgId | 是 | int | 库ID |

#### 返回结果

正常返回 HTTP 200

---

### 退出库
	quitLib(final DataListener listener, final int orgId)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| listener | 是 | DataListener | 从服务器获得数据后的回调 |
| orgId | 是 | int | 库ID |

#### 返回结果

	正常返回 HTTP 200

---


## **文件API**

### 锁定文件
	lock(final String fullPath, final int mountId, int lock)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| fullPath | 是 | string | 文件的路径 |
| mountId | 是 | int | 库空间ID |
| lock | 是 | int | 锁定或解锁的标示，"lock"：锁定，"unlock":解锁 |

#### 返回结果

正常 HTTP 200

---

### 文件最近更新列表
	getFileUpdates(int mountId, long msdateline, int size)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | 库空间id |
| msdateline | 是 | int | 查询当前时间戳(毫秒)之前的文件, 第一次0 |
| size | 否 | int | 获取数量, 默认500 |

#### 返回结果

	| 字段 | 类型 | 说明 |
    | --- | --- | --- |
    | dateline | int | 时间戳（13位精确到毫秒） |
    | list | Array | 格式见下 |
    
    | 字段 | 类型 | 说明 |
    | --- | --- | --- |
    | hash | string | 路径hash |
    | dir | int | 是否文件夹 |
    | fullpath | string | 文件路径 |
    | filehash | string | 文件hash |
    | filesize | bigint | 文件大小 |
    | last_member_id | int | 文件最后修改人 |
    | last_dateline | int | 文件最后修改时间戳(10位精确到秒) |
    | property | string | 属性, discuss:讨论数 |

---

### 获取文件分享链接
	getFileLink(String fullPath, int mountId, final String deadline,
                                  final String auth, final String password, final String scope, final String day)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| fullPath | 是 | string | fullpath |
| mountId | 是 | int | mount_id |
| deadline | 否 | string | 过期的时间戳,-1 表示永不失效 ，不传默认过期时间2天 |
| auth | 否 | string | 见下表 ，不传表示无权限 |
| password | 否 | string | 是否需要密码，不传表示不设置密码 |
| scope | 否 | string | 0 表示所有人都可访问; 1 表示只允许企业成员访问 |
| day | 否 | string | 过期天数 0为自定义时间;过期时间不会按这个计算，这个用于设置时的回显 |

| 需要传的值 | 预览 | 下载 | 上传 |
| --- | --- | --- | --- |
| 100 | O | X | X |
| 110 | O | O | X |
| 111 | O | O | O |
| 101 | O | X | O |
| 001 | X | X | O |

#### 返回结果

| 字段 | 类型 | 说明 |
| --- | --- | --- |
| code | string | 链接唯一code |
| link | string | 以http://开头的文件链接URL |
| qr_url | string | 以http://开头的二维码图片链接，后面可以附加'&width=250' |

---

### 设置文件描述
	setFileKeyWord(int mountId, String fullPath, String keywords)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | mount_id |
| fullPath | 是 | string | 文件的路径 |
| keywords | 是 | string | 关键字 |

#### 返回结果

正常 HTTP 200

---

### 获取收藏夹信息(文件)
	getFavoriteInfo(int type)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| type | 是 | int | 星标类型 |

#### 返回结果

| 字段 | 类型 | 说明 |
| --- | --- | --- |
| list | Array | 格式见下 |

| 字段 | 类型 | 说明 |
| --- | --- | --- |
| mount_id | string | mount_id |
| hash | string | 路径hash |
| dir | string | 是否文件夹 |
| fullpath | string | 文件路径 |
| filename | string | 文件名称 |
| last_member_name | string | 文件最后修改人 |
| last_dateline | string | 文件最后修改时间戳(10位精确到秒) |
| filehash | string | 文件hash(仅文件返回) |
| filesize | string | 文件大小(仅文件返回) |
| uri | string | 下载地址 (仅文件返回) |
| preview | string | 预览地址 (仅文件返回) |
| thumbnail | string | 缩略图地址 (仅文件返回) |

---

### 添加收藏(文件)
	addFavorities(final int mountid, final String fullpath, final int type)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mount_id | 是 | int | MOUNT_ID |
| fullpath | 是 | string | 文件路径 |
| favorite_type | 否 | int | 星标类型 |

#### 返回结果

正常 HTTP 200

---

### 删除收藏(文件)
	delFavorities(final int mountid, final String fullpath, final int type)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mount_id | 是 | int | MOUNT_ID |
| fullpath | 是 | string | 文件路径 |
| favorite_type | 否 | int | 星标类型 |

#### 返回结果

正常 HTTP 200

---

### 清空收藏(文件)
	clearFavorities()
	
#### 参数

（无）

#### 返回结果

正常 HTTP 200

---

### 获取24小时内修改的文件
	getFileRecentModified()
	
#### 参数

（无）

#### 返回结果

同获取文件列表

---

### 获取锁定文件信息
	getFileLocked()
	
#### 参数

（无）

#### 返回结果

| 字段 | 类型 | 说明 |
| --- | --- | --- |
| list | Array | 格式见下 |

| 字段 | 类型 | 说明 |
| --- | --- | --- |
| mount_id | string | mount_id |
| hash | string | 路径hash |
| dir | string | 是否文件夹 |
| fullpath | string | 文件路径 |
| filename | string | 文件名称 |
| last_member_name | string | 文件最后修改人 |
| last_dateline | string | 文件最后修改时间戳(10位精确到秒) |
| filehash | string | 文件hash(仅文件返回) |
| filesize | string | 文件大小(仅文件返回) |
| uri | string | 下载地址 (仅文件返回) |
| preview | string | 预览地址 (仅文件返回) |
| thumbnail | string | 缩略图地址 (仅文件返回) |

---

### 获取对应路径的权限
	getPermissionOfList(int mountId, String fullPath)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | mount_id |
| fullPath | 是 | string | 文件的路径 |

#### 返回结果

同获取文件列表

---

### 获取文件列表
	getFileListSync(final int mountId, final String fullPath, int start, int size)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | mount_id |
| fullPath | 是 | int | 文件的路径 |
| start | 否 | int | 开始条数 |
| size | 否 | int | 获取数量(默认100) |

#### 返回结果

| 字段 | 类型 | 说明 |
| --- | --- | --- |
| count | int | 文件总数 |
| list | Array | 格式见下 |

| 字段 | 类型 | 说明 |
| --- | --- | --- |
| mount_id | int | mount id |
| hash | string | 路径hash |
| dir | int | 是否文件夹 |
| fullpath | string | 文件路径 |
| filename | string | 文件名称 |
| filehash | string | 文件hash |
| filesize | bigint | 文件大小 |
| publish | int | 是否公开 |
| create_member_name | string | 文件创建人 |
| create_dateline | int | 文件创建时间戳 |
| last_member_name | string | 文件最后修改人 |
| last_dateline | int | 文件最后修改时间戳(10位精确到秒) |
| thumbnail | string | 文件小缩略图 |
| property | array | 格式见下 |

| 字段 | 类型 | 说明 |
| --- | --- | --- |
| permisson | array | 文件权限数组,见权限说明 |
| discuss | int | 文件讨论数 |
| tag | string | 文件标签，分号分割 |
| collection_type | string | 收集库类型: public / private |

#### 文件/库上的权限说明
| 权限 | 说明 |
| --- | --- |
| file_sync | 文件同步，备注 用于判断是否允许库同步 |
| file_read | 文件读取，备注 用于判断可否下载，复制，缓存 |（剪切功能不受这个功能权限影响）
| file_write | 文件写入，备注 用于判断库或文件夹可否上传文件，以及重命名，新建，粘贴 |
| file_preview | 文件预览， 备注 用户判断可否预览文件；ps：如遇无该权限而有file_read权限，则默认拥有该权限 |
| file_delete | 文件删除， 备注 用于判断可否删除文件，剪切 |
| file_recycle | 查看回收站， 备注 用于判断可否查看回收站 |
| file_delete_com | 文件彻底删除， 备注 用于判断可否彻底删除文件 |
| file_history | 文件历史版本， 备注 用于判断可否查看，恢复，历史版本 |
| file_link | 文件外链， 备注 用于判断是否拥有外链/云附件的分享权限 |
| ent_org | 库管理， 备注 用于判断是否允许管理库成员 |
| org_create | 创建库权限， 备注 用户判断是否允许创建库 |
| ent_group | 组织架构节点管理， 备注 用于判断是否允许管理组织架构 |

---

### 制定hash文件
	getFileListByHashs(int mountId, int start, int size, String[] hashs)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | mount_id |
| start | 否 | int | 开始条数  |
| size | 否 | int | 获取数量(默认100) |
| hashs | 是 | string[] | json字符串(获取指定hash文件) |

#### 返回结果

同获取文件列表

---

### 上传文件
	uploadFile(int mountId, String fullPath, String opName, String localPath, boolean overWrite)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | mount_id |
| fullPath | 是 | string | 文件路径 |
| opName | 否 | string | 上传人名称 |
| localPath | 是 | string | 本地路径 |
| overWrite | 是 | boolean | 是否重写 |

#### 返回结果

| 字段 | 类型 | 说明 |
|------|------|------|
| hash | string | 路径hash |
| fullpath | string | 文件夹的路径 |
| filehash | string | 文件的sha1值 |
| filesize | bigint | 文件的大小 |

---

### 批量删除文件文件夹
	batchDeleteFileAsync(final int mountId, final ArrayList<String> fullPaths)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | mount_id |
| fullPaths | 是 | ArrayList | 文件路径 |

#### 返回结果

正常返回 HTTP 200

---

### 文件复制
	fileBatchCopy(final int mountId, final ArrayList<String> fullPaths,
                                    final int targetMountId, final String targetFullPath)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | mount_id |
| fullPaths | 是 | ArrayList | 文件路径 |
| targetMountId | 是 | int | 复制到的mount_id |
| targetFullPath | 是 | string | 复制到的路径 |

#### 返回结果

<table class="table table-bordered table-striped">
   <tr>
      <td>字段</td>
      <td>类型</td>
      <td>说明 </td>
   </tr>
   <tr>
      <td>hash</td>
      <td>string</td>
      <td>复制后文件(夹)的路径hash</td>
   </tr>
   <tr>
      <td>fullpath</td>
      <td>string</td>
      <td>复制后文件(夹)的路径</td>
   </tr>
   <tr>
      <td>filehash</td>
      <td>string</td>
      <td>文件的sha1值(仅复制文件返回)</td>
   </tr>
   <tr>
      <td>filesize</td>
      <td>bigint</td>
      <td>文件的大小(仅复制文件返回)</td>
   </tr>
</table>

---

### 文件移动
	fileBatchMove(final int mountId, final ArrayList<String> fullPaths,
                                    final int targetMountId, final String targetFullPath)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | mount_id |
| fullPaths | 是 | ArrayList | 文件路径 |
| targetMountId | 是 | int | 复制到的mount_id |
| targetFullPath | 是 | string | 复制到的路径 |

#### 返回结果

正常返回 HTTP 200

---

### 改变收藏夹名称操作
	changeFavoritesName(final int type, final String name)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| type | 是 | int | 数字1-6代表各种类型 |
| name | 是 | string | 名称 |

#### 返回结果

正常返回 HTTP 200

---

### 获取收藏夹名称
	getFavoritesName()
	
#### 参数

（无）

#### 返回结果

| 字段 | 类型 | 说明 |
| --- | --- | --- |
|favorite_names|Array|收藏夹各种星标的用户自定义名称列表|

---

### 根据文件filehash获取文件路径
	getDownloadFileUrlByFileHash(int mountId, String filehash)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | mount_id |
| filehash | 是 | string | filehash |

#### 返回结果

| 字段 | 类型 | 说明 |
| --- | --- | --- |
| filesize| bigint | 文件大小 |
| urls | Array | 包含URL的数组 |

---

### 根据filehash获取文件
	getUrlByFileHash(final int mountId, final String fileHash, final String net, 
	                                    final boolean isOpen, final DataListener listener)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | mount_id |
| filehash | 是 | string | filehash |
| net | 否| string | 返回下载地址公网/内网(in-内网，不传默认公网) |
| isOpen | 否 | string | 是否打开改文件(不传默认false) |
| listener | 是 | DataListener | 从服务器获得数据后的回调 |

#### 返回结果

| 字段 | 类型 | 说明 |
| --- | --- | --- |
| filesize| bigint | 文件大小 |
| urls | Array | 包含URL的数组 |

---

### 设置文件权限
	setFilePermission(final int mountId, final String fullpath, 
	                                    final String permission, final boolean isGroup)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | mount_id |
| fullpath | 是 | string | 文件fullpath |
| isGroup | 否 | string | 是否为设置部门权限, 1:是;0:否 |
| permission | 否 | json | 格式如下 |

#### 权限格式例子
 	{
 		成员ID|部门ID(int): [
 			权限1(string),
 			...
 		],
 		...
 	}

#### 返回结果

正常返回 HTTP 200

#### 注意事项
* 非库管理员只允许查看，不能设置
* 拥有同步权限的时候，预览，读取，编辑与删除都不允许设置
* 读取权限需要预览权限，编辑权限需要读取与预览权限

---

### 获取成员的文件夹权限
	getMemberFolderPermission(final int mountId, final String fullpath, int start, int size)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | mount_id |
| fullpath | 是 | string | 文件fullpath |
| start | 否 | int | start |
| size | 否 | int | 返回大小 |

#### 返回结果

	{
		list: {
			member_id: 成员ID(int),
			member_name: 成员名称(string),
			member_email: 成员邮箱地址(string),
			fullpath: 文件fullpath(string),
			permissions: [ //成员在文件上权限
				权限1(string),
				...
			],
			permission_str: [ //成员在库上的权限名
				权限名称1(string),
				...
			]
		}
	}

---

### 获取部门文件夹权限
	getGroupFolderPermission(final int mountId, final String fullpath, int start, int size)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | mount_id |
| fullpath | 是 | string | 文件fullpath |
| start | 否 | int | start |
| size | 否 | int | 返回大小 |

#### 返回结果

	{
		list: {
			group_id: 部门ID(int),
			group_name: 部门名称(string),
			fullpath: 文件fullpath(string),
			permissions: [ //部门在文件上权限
				权限1(string),
				...
			],
			permission_str: [ //部门在库上的权限名
				权限名称1(string),
				...
			]
		}
	}

---

### 获取单文件数据
	getFileInfoSync(final String fullPath, int mountId, String hid)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| fullPath | 是 | int | 文件路径 |
| mountId | 是 | string | mount_id |
| hid | 否 | string | 版本ID |

#### 返回结果

| 字段 | 类型 | 说明 |
| --- | --- | --- |
| hash | string | 路径hash |
| dir | string | 是否文件夹 |
| fullpath | string | 文件路径 |
| filename | string | 文件名称 |
| last_member_name | string | 文件最后修改人 |
| last_dateline | string | 文件最后修改时间戳(10位精确到秒) |
| filehash | string | 文件hash(仅文件返回) |
| filesize | string | 文件大小(仅文件返回) |
| uri | string | 文件下载地址 (仅文件返回) |
| preview | string | 预览地址 (仅文件返回) |
| thumbnail | string | 缩略图地址 (仅文件返回) |

---

### 根据hash获取文件信息
	getFileInfoByHash(String hash, int mountId)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| hash | 是 | string | 路径hash |
| mountId | 是 | int | mount_id |

#### 返回结果

| 字段 | 类型 | 说明 |
| --- | --- | --- |
| hash | string | 路径hash |
| dir | string | 是否文件夹 |
| fullpath | string | 文件路径 |
| filename | string | 文件名称 |
| last_member_name | string | 文件最后修改人 |
| last_dateline | string | 文件最后修改时间戳(10位精确到秒) |
| filehash | string | 文件hash(仅文件返回) |
| filesize | string | 文件大小(仅文件返回) |
| uri | string | 文件下载地址 (仅文件返回) |
| preview | string | 预览地址 (仅文件返回) |
| thumbnail | string | 缩略图地址 (仅文件返回) |

---

### 获取文件夹属性
	getFolderAttribute(int mountId, String fullPath, String hash)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | mount_id |
| fullPath | 是 | string | 文件的路径 |
| hash | 是 | string | 路径hash |

#### 返回结果

| 字段 | 类型 | 说明 |
| --- | --- | --- |
| file_count | int | 文件数量 |
| folder_count | int | 文件夹数量 |

---

### 打开文件
	getDownloadFileUrlByPath(final int mountId, final String fullPath, String hid)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | mount_id |
| fullPath | 是 | string | 文件的路径 |
| hid | 否 | string | 版本ID(该版本文件是否存在) |

#### 返回结果

| 字段 | 类型 | 说明 |
| --- | --- | --- |
| hash | string | 路径hash |
| filehash | string | 文件hash |
| filesize | string | 文件大小 |
| dateline | long | 时间戳(毫秒)|
| lock | int | 锁定状态|
| lock_member_id | int | 锁定人ID |
| uris | string | 文件下载多地址 |

---

### 文件是否存在
	fileExistByHash(final String hash, final int mountId)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | mount_id |
| hash | 是 | string | 路径hash |

#### 返回结果

| 字段 | 类型 | 说明 |
| --- | --- | --- |
| hash | string | 路径hash |
| fullpath | string | 文件的路径 |

	 fileExistByFullPath(final String fullPath, final int mountId)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | mount_id |
| fullPath | 是 | string | 文件的路径 |

#### 返回结果

| 字段 | 类型 | 说明 |
| --- | --- | --- |
| hash | string | 路径hash |
| fullpath | string | 文件的路径 |
   
---

### 创建文件夹
	createFolder(int mountId, String fullpath)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | mount_id |
| fullpath | 是 | string | 文件夹路径 |

#### 返回结果

| 字段 | 类型 | 说明 |
|------|------|------|
| hash | string | 路径hash |
| fullpath | string | 文件夹的路径 |

---

### 新建文件、文件夹
	addFile(int mountId, String fullpath, String filehash, long filesize)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| fullpath | 是 | string | 文件路径 |
| filehash | 是 | string | 文件hash |
| filesize | 是 | long | 文件大小 |

#### 返回结果

| 字段 | 类型 | 说明 |
|------|------|------|
| state | int | 是否需要上传(0-需要，1-不需要) |
| hash | string | 路径hash |
| dateline | long | 同步时间戳 |
| version | int | 版本 |
| fullpath | string | 文件夹的路径 |
| filehash | string|文件hash|
| filesize | int |文件大小|
| servers | array | 上传服务器地址 **deprecated** |
| cache_servers | array |缓存上传服务器地址 **deprecated** |
| uploads | array | 上传服务器 [hostname,hostname-in,port,https,path] |
| cache_uploads | array | 缓存上传服务器 [hostname,hostname-in,port,https,path] |

---

### 离线下载
	createOffline(int mountId, String path, String fileName, String downloadUrl, 
	                                        String token, final DataListener listener)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | mount_id |
| path | 是 | string | 上级文件夹路径 |
| fileName | 是 | string | 文件名称 |
| downloadUrl | 是 | string | 要离线的文件url |
| token | 是 | string | 授权成功后获取的access_token |
| listener | 是 | DataListener | 从服务器获得数据后的回调 |

#### 返回结果

通过socket通知(小鲸助手[sys:member_id])

	{
		act:offline,
		metadata:{
			mount_id,
			hash,
			fullpath,
			filehash,
			filesize,
			state:1-成功,0-失败,
			error:错误描述(失败才有)
		}
	}

---

### 获取文件（夹）操作历史
	getHistory(final int mountId, final String fullPath)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | mount_id |
| fullpath | 是 | string | 文件夹路径 |

#### 返回结果
```
    {
       count:历史总数
       list:[
           {
           		hid://历史版本ID
                 mount_id: 123,
                 fullpath: "文件路径",
                 type: 1, // 文件操作类型（文件修改、文件讨论）
                 act: 1, // 具体文件操作类型
                 filesize:123,
                 member_id: 123, // 操作人
                 dateline: 123456789 // 时间戳
           }
           ...
         ]
    }
```
---

### 恢复文件
	revert(final String fullPath, final int mountId, final String hid)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | 库空间id |
| hid | 是 | int | 要还原的版本ID |
| fullpath | 是 | string | 文件夹路径 |

#### 返回结果

正常 HTTP 200

---

### 文件转存
	fileSave(int mountId, String fileName, String fileHash, long fileSize, int targetMountId, String targetFullPath)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | 要转存的mount_id |
| fileName | 是 | string | 要转存文件名称 |
| fileHash | 否 | string | 要转存文件hash(转存文件时) |
| fileSize | 否 | int | 要转存文件大小(转存文件时) |
| targetMountId | 是 | string | 转存到的mount_id |
| targetFullPath | 是 | string | 转存到的路径 |

#### 返回结果

	{
		hash:新的hash
	}


---

### 搜索文件
	fileSearch(final String keyword, final int mountId)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | mount_id |
| keyword | 是 | string | 关键字 |

#### 返回结果

	同获取文件夹列表
	全文搜索时，content返回全文匹配高亮内容

---

### 文件添加备注
	addFileRemark(final int mountId, final String fullPath, final String message)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | mount_id |
| fullpath | 是 | string | 文件夹路径 |
| message | 是 | string | 备注内容 |

#### 返回结果

正常返回 HTTP 200

---

### 文件备注列表
	getFileRemarkList(final int mountId, final String fullPath, final int start, final int size)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | mount_id |
| fullpath | 是 | string | 文件夹路径 |
| start | 否 | int | 开始条数 |
| size | 否 | int | 获取数量(默认20) |

#### 返回结果

```
{
  count:总数
  list:[
      {
            mount_id: 123,
            fullpath: "文件路径",
            type: 1, // 文件操作类型（文件修改、文件讨论）
            act: 1, // 具体文件操作类型
            filesize:123,
            member_id: 123, // 操作人
            dateline: 123456789 // 时间戳
      }
      ...
    ]
}
```

---

## **用户API**

### 获取用户最近访问文件
	getLastVisitFile(final DataListener listener, final int size)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| listener | 是 | DataListener | 从服务器获得数据后的回调 |
| size | 是 | int | 获取条数（默认20） |

#### 返回结果

| 字段 | 类型 | 说明 |
| --- | --- | --- |
| list | Array | 格式见下 |

| 字段 | 类型 | 说明 |
| --- | --- | --- |
| mount_id | string | mount_id |
| hash | string | 路径hash |
| dir | string | 是否文件夹 |
| fullpath | string | 文件路径 |
| filename | string | 文件名称 |
| create_member_name | string | 文件创建人 |
| last_member_name | string | 文件最后修改人 |
| last_dateline | string | 文件最后修改时间戳(10位精确到秒) |
| filehash | string | 文件hash |
| filesize | string | 文件大小 |

---

### 添加快捷方式
	addShortCuts(final DataListener listener,final int value, final int type)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| listener | 是 | DataListener | 从服务器获得数据后的回调 |
| value | 是 | int | 值(可以是favorite_type或mount_id的值) |
| type | 是 | int | 类型(1-智能文件夹,2-库,3-文件夹) |

#### 返回结果

    {
	    type,//类型(1-智能文件夹,2-库,3-文件夹)
	    value,//值(可以是favorite_type或mount_id的值)
	    hash,//文件夹hash
	    dateline//加入时间
	},

---

### 获取快捷方式列表
	getShortCuts()
	
#### 参数

（无）

#### 返回结果

| 字段 | 类型 | 说明 |
| --- | --- | --- |
| list |array |列表 |
    
 	{
	    'list':
	        [
	        	{
	        		type,//类型(1-智能文件夹,2-库,3-文件夹)
	        		value,//值(可以是favorite_type或mount_id的值)
	        		hash,//文件夹hash
	        		dateline//加入时间
	        	},
	        	...
	        ]
    }
---

### 删除快捷方式
	delShortCuts(final DataListener listener,final int value, final int type)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| listener | 是 | DataListener | 从服务器获得数据后的回调 |
| value | 是 | int | 值(可以是favorite_type或mount_id的值) |
| type | 是 | int | 类型(1-智能文件夹,2-库,3-文件夹) |

#### 返回结果

正常返回 HTTP 200

---

## **通讯录API**

### 获取部门中的部门
	getGroupFromGroup(int entId, int groupId)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| entId | 是 | int | 企业ID |
| groupId | 是 | int | 部门ID，为0的时候，拿第一层部门 |

#### 返回结果

| 名称 | 类型 | 说明 |
| --- | --- | --- |
| list | array | 部门列表 |
list的数据结构

	[
		{
			id:部门ID(int),
			out_id: 外部ID(string),
			ent_id: 企业ID(int),	
			name:部门名称(string),
			parent_id:父级部门ID(int),//0为根节点
			state:部门状态(int),//1为正常0为禁用
			child:含有子部门的数量(int),
			code:部门code(string),
			count: 部门成员以及子部门成员个数(int),
			checked:是否选中(int)//1为是,0为否
		},
		...
	]
	
---

### 搜索联系人-部门
	searchContactGroups(int entId, String keyWord)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| entId | 是 | int | 企业ID |
| keyWord | 是 | string | 部门名的关键字 |

#### 返回结果

| 名称 | 类型 | 说明 |
| --- | --- | --- |
| list | array | 部门列表 |
list的数据结构

	[
		{
			id:部门ID(int),
			out_id: 外部ID(string),
			ent_id: 企业ID(int),	
			name:部门名称(string),
			parent_id:父级部门ID(int),//0为根节点
			state:部门状态(int),//1为正常0为禁用
			child:含有子部门的数量(int),
			code:部门code(string),
			count: 部门成员以及子部门成员个数(int)
		},
		...
	]

---

### 获取部门成员列表
	getMemberFromGroup(int entId, int groupId, int start, int order, String key, int showChild)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| entId | 是 | int | 企业ID |
| groupId | 否 | int | 所在部门ID |
| start | 否 | int | 开始位置 |
| order | 否 | int | 排序，默认 desc |
| key | 否 | string | 查询关键词，邮箱或成员名 |
| showChild | 否 | int | 是否显示子部门成员 |

#### 返回结果

| 名称 | 类型 | 说明 |
| --- | --- | --- |
| list | array | 成员列表 |
| count | int | 成员数量 |

list的数据结构

	[
		{
			ent_id: 企业ID(int),
			member_id: 成员ID(int),
			out_id: 外部ID(string),
			user_id: 帐号ID(string),
			member_name:成员名称(string),
			member_title:成员职位(string),
			member_letter:成员首字母拼音(string),
			member_email:成员邮箱地址(string),
			member_phone:成员联系电话(string),
			state: 成员状态(bool)//1:正常;0:禁用
		},
		...
	]

---

### 获取成员信息
	getMemberInfo(int entId, int memberId)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| entId | 是 | int | 企业ID |
| memberId | 是 | int | 成员ID |

#### 返回结果

	{
		is_super_admin: 是否为超管成员(bool), //1为是, 0为否
		name: 成员个人名称(string),
		phone: 成员个人联系电话(string),
		email: 成员个人联系邮箱(string)，
		member_id:成员ID(int),
		out_id: 外部ID(string),
		user_id: 帐号ID(string),
		ent_id: 企业ID(int),
		wechat_id: 企业微信ID(int), //未绑定企业微信则返回NULL
		member_name:成员名称(string),
		member_title:成员职位(string),
		member_letter:成员首字母拼音(string),
		member_email:成员邮箱地址(string),
		member_phone:成员联系电话(string),
		disable_new_device: 是否禁用新设备(int), //1为是, 0为否
		state: 成员状态(int),//1为正常,0为禁用
		addtime: 添加时间(timestamp),
		expire: 临时成员过期时间(timestamp), //非临时成员返回为''
		enable_create_org: 创建库权限(int), //1为开启,0为关闭
		enable_publish_notice: 发布公告权限(int), //1为开启,0为关闭
		enable_manage_groups: 允许管理的部门(string) #以 "|" 符号分割，如 |0|1|2|
		member_groups: 成员所属所有部门(array) //返回结构如【获取成员所属部门】
	}

---

### 获取成员所属部门列表
	getContactMemberGroups(final int entId, final int memberId, final DataListener listener)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| entId | 是 | int | 企业ID |
| memberId | 是 | int | 成员ID |
| listener | 是 | DataListener | 从服务器获得数据后的回调 |

#### 返回结果

| 名称 | 类型 | 说明 |
| --- | --- | --- |
| list | array | 部门列表 |

list数据结构

	[
		{
			id:部门ID(int),
			name: 部门名称(string),
		},
		...
	]

---

### 添加通讯录部门
	addContactGroup(final int entId, final int groupId, final String name, final DataListener listener)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| entId | 是 | int | 企业ID |
| groupId | 是 | string | 父级部门ID，根部门为0 |
| name | 是 | string | 部门名称 |
| listener | 是 | DataListener | 从服务器获得数据后的回调 |

#### 返回结果

	{
		id:部门ID(int),
		out_id: 外部ID(string),
		name: 部门名称(string),
		parent_id: 父级部门ID(int),
		code: 部门CODE(string),
		state: 部门状态(int)
	}

---

### 改变通讯录中部门名字
	changeContactGroupName(final int entId, final int groupId,
                                             final String name, final DataListener listener)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| entId | 是 | int | 企业ID |
| groupId | 是 | string | 部门ID |
| name | 否 | string | 部门名称，不传则保持不变 |
| listener | 是 | DataListener | 从服务器获得数据后的回调 |

#### 返回结果

	{
		id:部门ID(int),
		out_id: 外部ID(string),
		name: 部门名称(string),
		parent_id: 父级部门ID(int),
		code: 部门CODE(string),
		state: 部门状态(int)
	}

---

### 删除通讯录的部门
	delContactGroup(final int entId, final int groupId, final DataListener listener)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| entId | 是 | int | 企业ID |
| groupId | 是 | string | 部门ID |
| listener | 是 | DataListener | 从服务器获得数据后的回调 |

#### 返回结果

正常返回 HTTP 200

---

### 添加通讯录成员
	addContactMember(final int entId, final String name, final String groupId, final String email,
                                       final String phone, final String password, final int publishNotice, 
                                            final int createOrg, final DataListener listener)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| entId | 是 | int | 企业ID |
| name | 是 | int | 显示名 |
| groupId | 否 | string | 部门ID,不传默认添加到根部门,不添加到任何部门传-1 |
| email | 否 | string | 邮箱地址 |
| phone | 否 | string | 联系电话  |
| password | 否 | string | 用户登录密码,对于未注册的用户有用 |
| publishNotice | 否 | int | 是否允许发公告 1:允许; 0:禁止 |
| createOrg | 否 | int | 是否允许创建库 1:允许; 0:禁止 |
| listener | 是 | DataListener | 从服务器获得数据后的回调 |

#### 返回结果


	{
		member_id:成员ID(int),
		out_id: 外部ID(string),
		user_id: 帐号ID(string),
		ent_id: 企业ID(int),
		wechat_id: 企业微信ID(int), //未绑定企业微信则返回NULL
		member_name:成员名称(string),
		member_title:成员职位(string),
		member_letter:成员首字母拼音(string),
		member_email:成员邮箱地址(string),
		member_phone:成员联系电话(string),
		disable_new_device: 是否禁用新设备(int), //1为是, 0为否
		state: 成员状态(int),//1为正常,0为禁用
		addtime: 添加时间(timestamp),
		expire: 临时成员过期时间(timestamp), //非临时成员返回为''
		enable_create_org: 创建库权限(int), //1为开启,0为关闭
		enable_publish_notice: 发布公告权限(int), //1为开启,0为关闭
		enable_manage_groups:允许管理的部门(string) #以 "|" 符号分割，如 |0|1|2|
	}

---

### 更新通讯录中成员信息
	updateContactMember(final int entId, final String memberIds,
                                          final String groupIds, final int enablePublishNotice,
                                          final int enableCreateOrg, final String memberPhone, final DataListener listener)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| entId | 是 | int | 企业ID |
| memberIds | 是 | int | 需要修改的用户ID,以逗号分隔;有些非公共属性对于修改多个无效 |
| groupIds | 否 | string | 所属的所有部门ID，以逗号分隔 |
| enablePublishNotice | 否 | int | 是否允许发公告 1:允许; 0:禁止 |
| enableCreateOrg | 否 | int | 是否允许创建库 1:允许; 0:禁止 |
| memberPhone | 否 | string | 联系电话  |
| listener | 是 | DataListener | 从服务器获得数据后的回调 |

#### 返回结果

	{
		list :{
			member_id: {
				member_id:成员ID(int),
				out_id: 外部ID(string),
				user_id: 帐号ID(string),
				ent_id: 企业ID(int),
				wechat_id: 企业微信ID(int), //未绑定企业微信则返回NULL
				member_name:成员名称(string),
				member_title:成员职位(string),
				member_letter:成员首字母拼音(string),
				member_email:成员邮箱地址(string),
				member_phone:成员联系电话(string),
				disable_new_device: 是否禁用新设备(int), //1为是, 0为否
				state: 成员状态(int),//1为正常,0为禁用
				addtime: 添加时间(timestamp),
				expire: 临时成员过期时间(timestamp), //非临时成员返回为''
				enable_create_org: 创建库权限(int), //1为开启,0为关闭
				enable_publish_notice: 发布公告权限(int), //1为开启,0为关闭
				enable_manage_groups:允许管理的部门(string) #以 "|" 符号分割，如 |0|1|2|
			}
			......
		}
	}

---

### 删除通讯录成员
	delContactMember(final int entId, final String removeMemberIds, final int transferMemberId, final DataListener listener)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| entId | 是 | int | 企业ID |
| removeMemberIds | 是 | int | 需要移除的用户ID,以逗号分隔 |
| transferMemberId | 否 | int | 转移被删除成员拥有的企业库到该成员  |
| listener | 是 | DataListener | 从服务器获得数据后的回调 |

#### 返回结果

	[
		因同步帐号而无法删除的member_id(int),
		......
	]

---

### 获取库成员
	getOrgMembersList(int orgId, int entId, int start)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| orgId | 是 | int | 库ID |
| entId | 是 | int | 企业ID |
| start | 否 | int | 开始位置 |

#### 返回结果

	{
		list: [
			{
				org_id:库ID(int),
     			mount_id:库MOUNTID(int),
      			ent_id:企业ID(int),
      			member_id:成员ID(int),
				out_id: 外部ID(string),
				user_id: 帐号ID(string),
				member_name:成员名称(string),
				member_title:成员职位(string),
				member_letter:成员首字母拼音(string),
				member_email:成员邮箱地址(string),
				member_phone:成员联系电话(string),
				state: 成员状态(bool)//1:正常;0:禁用
			},
			......
		],
		count: 总数(int)
	}

---

### 获取企业所有部门
	getGroupTable(int entId, int start)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| entId | 是 | int | 企业ID |
| start | 否 | int | 开始位置 |

#### 返回结果

	{
		list: [
			{
				id:部门ID(int),
				out_id: 外部ID(string),
				ent_id: 企业ID(int),	
				name:部门名称(string),
				parent_id:父级部门ID(int),//0为根节点
				state:部门状态(int),//1为正常0为禁用				code:部门code(string),
				count: 部门成员以及子部门成员个数(int),
				addtime: 部门添加时间(timestamp)
				member_ids: [
					部门成员ID(int),
					...
				]
			},
			...
		],
		count: 部门总数
	}

---

### 获取企业成员
	getEntMembers(int entId, int start, String keyWord)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| entId | 是 | int | 企业ID |
| start | 否 | int | 开始位置 |
| keyWord | 否 | string | 查询关键词，邮箱或显示名 |

#### 返回结果

| 名称 | 类型 | 说明 |
| --- | --- | --- |
| list | array | 成员列表 |
| count | int | 成员数量 |

list的数据结构

	[
		{
			ent_id: 企业ID(int),
			member_id: 成员ID(int),
			out_id: 外部ID(string),
			user_id: 帐号ID(string),
			member_name:成员名称(string),
			member_title:成员职位(string),
			member_letter:成员首字母拼音(string),
			member_email:成员邮箱地址(string),
			member_phone:成员联系电话(string),
			state: 成员状态(bool)//1:正常;0:禁用
		},
		...
	]

---

### 根据id数据获取成员
	getEntMembersByIds(int entId, int start, String memberIdsStr)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| entId | 是 | int | 企业ID |
| start | 否 | int | 开始位置 |
| memberIdsStr | 否 | string | 查询的member_ids,用逗号分割 |

#### 返回结果

| 名称 | 类型 | 说明 |
| --- | --- | --- |
| list | array | 成员列表 |
| count | int | 成员数量 |

list的数据结构

	[
		{
			ent_id: 企业ID(int),
			member_id: 成员ID(int),
			out_id: 外部ID(string),
			user_id: 帐号ID(string),
			member_name:成员名称(string),
			member_title:成员职位(string),
			member_letter:成员首字母拼音(string),
			member_email:成员邮箱地址(string),
			member_phone:成员联系电话(string),
			state: 成员状态(bool)//1:正常;0:禁用
		},
		...
	]

---

### 搜索通讯录成员
	searchEntMember(int entId, int start, String keyWord)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| entId | 是 | int | 企业ID |
| start | 否 | int | 开始位置 |
| keyWord | 否 | string | 查询关键词，邮箱或显示名 |

#### 返回结果

| 名称 | 类型 | 说明 |
| --- | --- | --- |
| list | array | 成员列表 |
| count | int | 成员数量 |

list的数据结构

	[
		{
			ent_id: 企业ID(int),
			member_id: 成员ID(int),
			out_id: 外部ID(string),
			user_id: 帐号ID(string),
			member_name:成员名称(string),
			member_title:成员职位(string),
			member_letter:成员首字母拼音(string),
			member_email:成员邮箱地址(string),
			member_phone:成员联系电话(string),
			state: 成员状态(bool)//1:正常;0:禁用
		},
		...
	]

---

### 搜索通讯录联系人
	searchContactMembers(final int entId, final int start, final String keyWord,
                                           final int size, final DataListener listener)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| entId | 是 | int | 企业ID |
| start | 否 | int | 开始位置 |
| size | 否 | int | 数量，不传拿全部 |
| keyWord | 否 | string | 查询关键词，邮箱或显示名 |
| listener | 是 | DataListener | 从服务器获得数据后的回调 |

#### 返回结果

| 名称 | 类型 | 说明 |
| --- | --- | --- |
| list | array | 成员列表 |
| count | int | 成员数量 |

list的数据结构

	[
		{
			ent_id: 企业ID(int),
			member_id: 成员ID(int),
			out_id: 外部ID(string),
			user_id: 帐号ID(string),
			member_name:成员名称(string),
			member_title:成员职位(string),
			member_letter:成员首字母拼音(string),
			member_email:成员邮箱地址(string),
			member_phone:成员联系电话(string),
			state: 成员状态(bool)//1:正常;0:禁用
		},
		...
	]

---

### memberId 转 outId 同步方法
	getOutIdFromMemberIdSync(int[] memberIds, int entId)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| memberIds | 是 | int[] | 需要转换的成员ID，以逗号隔开 |
| entId | 是 | int | 企业ID |

#### 返回结果

	{
		list: [
			{
				ent_id: 企业ID(int),
				member_id: 成员ID(int),
				member_name: 成员名称(string),
				out_id: 外部ID(string),
				user_id: 帐号ID(string)
			},
			...
		]
	}

---

### outId 转 memberId 
	getMemberIdFromOutId(final int[] outIds, final int entId, final DataListener listener)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| outIds | 是 | int[] | 需要转换的成员OUTID，以逗号隔开 |
| entId | 是 | int | 企业ID |
| listener | 是 | DataListener | 从服务器获得数据后的回调 |

#### 返回结果

	{
		list: [
			{
				ent_id: 企业ID(int),
				member_id: 成员ID(int),
				member_name: 成员名称(string),
				out_id: 外部ID(string)
			},
			...
		],
		error_list: [
			{
				out_id: 外部ID(string)
			},
			...
		]
	}

---

### 检测成员是否已存在
	checkExistMember(final int entId, final String memberEmail, final DataListener listener)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| entId | 是 | int | 企业ID |
| memberEmail | 是 | string | 邮箱地址 |
| listener | 是 | DataListener | 从服务器获得数据后的回调 |

#### 返回结果


	{
		is_member: 是否已经是成员(bool),
		is_exist: 是否已注册够快(bool)
	}

---

### 添加部门成员
	addGroupMember(final int entId, final int groupId, final String memberIds, final DataListener listener)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| entId | 是 | int | 企业ID |
| groupId | 是 | int | 部门ID |
| memberIds | 是 | string | 被移除的成员ID，用逗号分割 |
| listener | 是 | DataListener | 从服务器获得数据后的回调 |

#### 返回结果

	[
		添加成功的成员ID(int)
	]

---

### 移除部门成员
	removeGroupMember(final int entId, final int groupId, final String memberIds, final DataListener listener)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| entId | 是 | int | 企业ID |
| groupId | 是 | int | 部门ID |
| memberIds | 是 | string | 被移除的成员ID，用逗号分割 |
| listener | 是 | DataListener | 从服务器获得数据后的回调 |

#### 返回结果

	[
		移除成功的成员ID(int)
	]

---

### 创建文件夹
	createFolder(int mountId, String fullpath)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | mount_id |
| fullpath | 是 | string | 文件夹路径 |

#### 返回结果

| 字段 | 类型 | 说明 |
|------|------|------|
| hash | string | 路径hash |
| fullpath | string | 文件夹的路径 |

---

## 常见问题
### 编码问题
#### 现象
* Windows 运行环境，中文文件名参数可能会导致，返回签名的报错信息。

#### 解决方法
**方案1:** 更换运行环境，使用 Linux 服务器		
**方案2:** 如果是命令行执行 jar 文件，终端上执行以下命令即可 
	
	java -Dfile.encoding=utf-8 XX.jar		
**方案3:** 如果使用的是 Apache Tomatcat，在 Java Options 上，添加 -Dfile.encoding=utf-8 即可。

<img src="Screenshot/1.png" alt="Apache Tomatcat" title="Apache Tomatcat" width="50%" height="50%" />  