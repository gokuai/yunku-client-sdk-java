# 够快云库 用户API Java SDK 使用说明

## 兼容

* Java 7 或者更高
* 支持 Android 系统

## 引用

以下配置中的`{version}`使用最新的JitPack版本:

[![](https://jitpack.io/v/gokuai/yunku-client-sdk-java.svg)](https://jitpack.io/#gokuai/yunku-client-sdk-java)

Gradle :

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
	        compile 'com.github.gokuai.yunku-client-sdk-java:YunkuAPILibrary:{version}'
	}
```
Maven：

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
	    <groupId>com.github.gokuai.yunku-client-sdk-java</groupId>
	    <artifactId>YunkuAPILibrary</artifactId>
	    <version>{version}</version>
	</dependency>
```

## 初始化

使用够快云库的用户API，需向你的企业管理员提出开发授权申请, 或联系我们 400-6110-860 以获取调用API的 `client_id` 和 `client_secret`

**初始化配置**

```
new ConfigHelper(clientId, secret)
                .webHost("http://webhost")
                .apiHost("http://webhost/m-api")
                .language("zh-CN")
                .config();
```

* `clientId`和`secret` 是够快分配的用户API授权
* `webHost` 是网站地址, 公有云默认为`http://yk3.gokuai.com`
* `apiHost` 是API地址, 公有云默认为`http://yk3.gokuai.com/m-api`
* `language` 多语言环境, `zh-CN`表示中文, `en-US`表示英文

## 参数使用

以下使用到的方法中，如果是string类型的非必要参数，如果是不传，则传`null`

## 云库API

	new YKHttpEngine（ClientId, ClientSecret）

### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| ClientId | 是 | string | 申请应用时分配的AppKey |
| ClientSecret | 是 | string | 申请应用时分配的AppSecret |

---

### 帐号密码登录

	login(String account, String password)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| account | 是 | string | 帐号 |
| password | 是 | string | 密码 |

#### 返回结果

```
{
    "expires_in": access_token有效期，以秒为单位,
    "access_token": 用于调用access_token，接口获取授权后的access token,
    "refresh_token": 用于刷新access_token 的 refresh_token，有效期7天,
    "dateline": 时间戳
}
```

---

### 企业一站式登录

	ssoLogin(String account, String entClientId, String entSecret)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| account | 是 | string | 帐号 |
| entClientId | 是 | string | 向企业管理员申请的企业client_id |
| entSecret | 是 | string | 向企业管理员申请的企业client_secret |

#### 返回结果

```
{
    "gkkey": 需要验证的gkkey
}
```

---

### 单点登录

	exchangeToken(String exchangeToken)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| exchangeToken | 是 | string | 使用第三方token换取够快token |

#### 返回结果

	{
    	access_token: 用于调用access_token，接口获取授权后的access token
    	expires_in: access_token的有效期，unix时间戳
    	refresh_token: 用于刷新access_token 的 refresh_token，有效期1个月, 企业授权该值为null
	}

---

### 获取库列表

	getLibraries()

#### 返回结果

```
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
```

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

### 获取库信息

	getLibraryInfo(int mountId, int orgId)
	getLibraryInfoByMountId(int mountId)
	getLibraryInfoByOrgId(int orgId)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | 库空间ID |
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

### 删除库

	deleteLibrary(int orgId)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| orgId | 是 | int | 库ID |

#### 返回结果

正常返回 HTTP 200

---

### 获取文件列表

	getLibraryFiles(int mountId, String fullpath, int start, int size)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | 库空间ID |
| fullpath | 是 | String | 文件夹路径 |
| start | 否 | int | 开始位置 |
| size | 否 | int | 返回数量 |

#### 返回结果

| 字段 | 类型 | 说明 |
| --- | --- | --- |
| count | int | 文件总数 |
| list | Array | 格式见下 |

| 字段 | 类型 | 说明 |
| --- | --- | --- |
| mount_id | int | mount id |
| hash | string | 文件ID |
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

### 获取指定文件

	getFilesByHashs(int mountId, ArrayList<String> hashs)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | 库空间ID |
| hashs | 是 | ArrayList<String> | 文件ID列表 |

#### 返回结果

同获取文件列表

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
| hash | string | 文件ID |
| dir | int | 是否文件夹 |
| fullpath | string | 文件路径 |
| filehash | string | 文件hash |
| filesize | bigint | 文件大小 |
| last_member_id | int | 文件最后修改人 |
| last_dateline | int | 文件最后修改时间戳(10位精确到秒) |
| property | string | 属性, discuss:讨论数 |

---

### 获取用户最近访问文件

	getRecentFiles(int size)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| size | 是 | int | 返回数量 |

#### 返回结果

同获取文件列表

---
	
### 文件分块上传

	uploadByBlock(String localFile, int mountId, String fullpath, boolean overwrite)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |	
|------|------|------|------|
| localFile | 是 | string | 文件本地路径 |
| mountId | 是 | int | 库空间ID |
| fullpath | 是 | string | 文件路径 |
| overwrite | 是 | bool | 是否覆盖已存在的文件 |

---

### 数据流分块上传

	uploadByBlock(InputStream stream, int mountId, String fullpath, boolean overwrite)
	
#### 参数 

| 参数 | 必须 | 类型 | 说明 |	
|------|------|------|------|
| inputStream | 是 | InputStream | 流数据 |
| mountId | 是 | int | 库空间ID |
| fullpath | 是 | string | 文件路径 |
| overwrite | 是 | bool | 是否覆盖已存在的文件 |

---  

### 文件删除

	filesDelete(int mountId, ArrayList<String> fullpaths)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | 库空间ID |
| fullpaths | 是 | ArrayList | 文件路径列表 |

#### 返回结果

正常返回 HTTP 200

---

### 文件复制

	filesCopy(int mountId, ArrayList<String> fullpaths, int targetMountId, String targetFullpath)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | 库空间ID |
| fullpaths | 是 | ArrayList | 原文件路径列表 |
| targetMountId | 是 | int | 目标库空间ID |
| targetFullpath | 是 | string | 目标路径 |

#### 返回结果

字段 | 类型| 说明
--- | --- | ---
hash | string | 复制后文件(夹)的路径hash
fullpath | string | 复制后文件(夹)的路径
filehash | string | 文件的sha1值(仅复制文件返回)
filesize | long | 文件的大小(仅复制文件返回)

---

### 文件移动

	filesMove(int mountId, ArrayList<String> fullpaths, int targetMountId, String targetFullpath)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | 库空间ID |
| fullpaths | 是 | ArrayList | 原文件路径列表 |
| targetMountId | 是 | int | 目标库空间ID |
| targetFullpath | 是 | string | 目标路径 |

#### 返回结果

正常返回 HTTP 200

---

### 获取文件信息

	getFileInfoByFullpath(int mountId, String fullpath, String hid)
	getFileInfoByHash(int mountId, String hash, String hid)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | 库空间ID |
| fullpath | 是 | string | 文件路径 |
| hash | 是 | string | 文件ID |
| hid | 否 | string | 版本ID |

#### 返回结果

| 字段 | 类型 | 说明 |
| --- | --- | --- |
| hash | string | 文件ID |
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

	getFolderAttribute(int mountId, String fullpath, String hash)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | 库空间ID |
| fullpath | 是 | string | 文件路径 |
| hash | 是 | string | 文件ID |

#### 返回结果

| 字段 | 类型 | 说明 |
| --- | --- | --- |
| file_count | int | 文件数量 |
| folder_count | int | 文件夹数量 |

---

### 获取文件下载地址

	getFileUrlByFullpath(int mountId, String fullpath, boolean open, String hid)
	getFileUrlByFileHash(int mountId, String filehash, boolean open)

#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | 库空间ID |
| fullpath | 是 | string | 文件路径 |
| hash | 是 | string | 文件ID |
| hid | 否 | string | 文件历史版本 |
| open | 否 | bool | 是否用于在线打开 |

#### 返回结果

| 字段 | 类型 | 说明 |
| --- | --- | --- |
| hash | string | 文件ID |
| filehash | string | 文件hash |
| filesize | long | 文件大小 |
| lock | int | 锁定状态 |
| uris | String[] | 文件下载地址 |

---

### 文件是否存在

	fileExistsByFullPath(int mountId, String fullpath)
	fileExistsByHash(int mountId, String hash)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | 库空间ID |
| fullpath | 是 | string | 文件路径 |
| hash | 是 | string | 文件ID |

#### 返回结果

| 字段 | 类型 | 说明 |
| --- | --- | --- |
| hash | string | 文件ID |
| fullpath | string | 文件路径 |
   
---

### 创建文件夹

	createFolder(int mountId, String fullpath)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | 库空间ID |
| fullpath | 是 | string | 文件夹路径 |

#### 返回结果

| 字段 | 类型 | 说明 |
|------|------|------|
| hash | string | 文件ID |
| fullpath | string | 文件夹的路径 |

---

### 创建文件

	addFile(int mountId, String fullpath, String filehash, long filesize, boolean overwrite)
	
**注意该接口仅创建文件, 如果filehash和filesize匹配系统中已存在的文件, 可实现秒传**
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | 库空间ID |
| fullpath | 是 | string | 文件路径 |
| filehash | 是 | string | 文件hash |
| filesize | 是 | long | 文件大小 |
| overwrite | 是| bool | 是否覆盖已存在的文件 |

#### 返回结果

| 字段 | 类型 | 说明 |
|------|------|------|
| state | int | 1 已秒传，0 需要上传文件 |
| hash | string | 文件ID |
| dateline | long | 同步时间戳 |
| version | int | 版本 |
| fullpath | string | 文件路径 |
| filehash | string| 文件hash |
| filesize | int | 文件大小 |
| servers | array | 上传服务器地址 **deprecated** |
| uploads | array | 上传服务器 [hostname,hostname-in,port,https,path] |

---

### 获取文件历史版本

	getFileHistory(int mountId, String fullpath)	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | 库空间ID |
| fullpath | 是 | string | 文件路径 |

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
       },
       ...
     ]
}
```
---

### 回滚文件版本

	fileRevert(int mountId, String fullpath, String hid)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | 库空间ID |
| fullpath | 是 | string | 文件路径 |
| hid | 是 | String | 文件历史版本 |

#### 返回结果

正常 HTTP 200

---

### 搜索文件

	fileSearch(int mountId, String keywords)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | 库空间ID |
| keyword | 是 | string | 关键字 |

#### 返回结果

	同获取文件夹列表
	全文搜索时，content返回全文匹配高亮内容

---

### 生成文件外链

	getFileLink(int mountId, String fullpath, String deadline, String auth, String password, String scope, String day)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | 库空间ID |
| fullpath | 是 | string | 文件路径 |
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
| code | string | 外链code |
| link | string | 外链地址 |
| qr_url | string | 外链二维码图片地址 |

---

### 设置文件标签

	setFileTag(int mountId, String fullpath, String tags)	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | 库空间ID |
| fullpath | 是 | string | 文件路径 |
| tags | 是 | string | 标签 |

#### 返回结果

正常 HTTP 200

---

### 获取收藏夹文件列表

	getFavoriteFiles(int start, int size)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| start | 是 | int | 开始位置 |
| size | 是 | int | 返回数量 |

#### 返回结果

| 字段 | 类型 | 说明 |
| --- | --- | --- |
| list | Array | 格式见下 |

| 字段 | 类型 | 说明 |
| --- | --- | --- |
| mount_id | string | mount_id |
| hash | string | 文件ID |
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

### 添加文件到收藏夹

	addFavoriteFile(int mountId, String fullpath)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | 库空间ID |
| fullpath | 是 | string | 文件路径 |

#### 返回结果

正常 HTTP 200

---

### 取消文件收藏

	delFavoriteFile(int mountId, String fullpath)	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | 库空间ID |
| fullpath | 是 | string | 文件路径 |

#### 返回结果

正常 HTTP 200

---

### 添加文件备注

	addFileRemark(int mountId, String fullpath, String text)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | 库空间ID |
| fullpath | 是 | string | 文件路径 |
| text | 是 | string | 备注内容 |

#### 返回结果

正常返回 HTTP 200

---

### 文件备注列表

	getFileRemarks(int mountId, String fullpath, int start, int size)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | 库空间ID |
| fullpath | 是 | string | 文件路径 |
| start | 否 | int | 开始位置 |
| size | 否 | int | 返回数量 |

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

### 锁定文件

	fileLock(int mountId, String fullpath, boolean lock)
	
#### 参数

| 参数 | 必须 | 类型 | 说明 |
| --- | --- | --- | --- |
| mountId | 是 | int | 库空间ID |
| fullpath | 是 | string | 文件路径 |
| lock | 是 | bool | true 锁定, false 解锁 |

#### 返回结果

正常 HTTP 200

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

<img src=https://repo.gokuai.cn/app/ImageResourceForMD/raw/master/YunkuJavaSDK/encoding.png alt="Apache Tomatcat" title="Apache Tomatcat" width="50%" height="50%" />  

### 语言问题

#### 现象

传入中文字段返回英文字段

#### 解决办法

更改接口的语言环境，代码如下

```
Locale locale = new Locale("zh-CN");
Locale.setDefault(locale);
```
