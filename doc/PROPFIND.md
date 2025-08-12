# PROPFIND 方法说明

## 请求体格式

PROPFIND 方法通常使用 XML 格式的请求体，主要用于查询 WebDAV 资源的属性。常见格式如下：

```xml
<?xml version="1.0" encoding="utf-8"?>
<propfind xmlns="DAV:">
  <prop>
    <displayname/>
    <getcontentlength/>
    <getlastmodified/>
    <creationdate/>
    <resourcetype/>
    <getetag/>
    <getcontenttype/>
    <lockdiscovery/>
    <supportedlock/>
    <source/>
    <quota-available-bytes/>
    <quota-used-bytes/>
    <owner/>
    <group/>
    <permissions/>
    <executable/>
    <hidden/>
    <readonly/>
    <iscollection/>
    <isfolder/>
    <isfile/>
    <lastaccessed/>
    <lastmodifiedby/>
    <createdby/>
    <size/>
    <contentlanguage/>
    <contentencoding/>
    <checksum/>
    <version/>
    <comment/>
    <!-- 可根据需要添加其他属性 -->
  </prop>
</propfind>
```

### 字段及含义

- `propfind`：根元素，表示属性查询请求。
    - `xmlns`：命名空间，通常为 DAV:。
- `prop`：包含需要查询的属性列表。
    - `displayname`：资源显示名称。
    - `getcontentlength`：资源内容长度（字节）。
    - `getlastmodified`：资源最后修改时间。
    - `creationdate`：资源创建时间。
    - `resourcetype`：资源类型（如 collection 表示目录）。
    - `getetag`：资源的实体标签（ETag）。
    - `getcontenttype`：资源内容类型（MIME 类型）。
    - `lockdiscovery`：锁信息。
    - `supportedlock`：支持的锁类型。
    - `source`：资源的源信息。
    - `quota-available-bytes`：可用配额（字节）。
    - `quota-used-bytes`：已用配额（字节）。
    - `owner`：资源所有者。
    - `group`：资源所属组。
    - `permissions`：权限信息。
    - `executable`：是否可执行。
    - `hidden`：是否隐藏。
    - `readonly`：是否只读。
    - `iscollection`：是否为集合（目录）。
    - `isfolder`：是否为文件夹。
    - `isfile`：是否为文件。
    - `lastaccessed`：最后访问时间。
    - `lastmodifiedby`：最后修改者。
    - `createdby`：创建者。
    - `size`：资源大小。
    - `contentlanguage`：内容语言。
    - `contentencoding`：内容编码。
    - `checksum`：内容校验值。
    - `version`：版本信息。
    - `comment`：备注。
    - 其他自定义属性。

## 返回值格式

返回体同样为 XML 格式，包含资源的属性信息。

```xml
<?xml version="1.0" encoding="utf-8"?>
<multistatus xmlns="DAV:">
  <response>
    <href>/path/to/resource</href>
    <propstat>
      <prop>
        <displayname>文件名</displayname>
        <getcontentlength>12345</getcontentlength>
        <getlastmodified>Mon, 12 Aug 2025 10:00:00 GMT</getlastmodified>
        <creationdate>Mon, 01 Jan 2024 09:00:00 GMT</creationdate>
        <resourcetype/>
        <getetag>"abc123etag"</getetag>
        <getcontenttype>text/plain</getcontenttype>
        <lockdiscovery/>
        <supportedlock/>
        <source/>
        <quota-available-bytes>100000000</quota-available-bytes>
        <quota-used-bytes>12345</quota-used-bytes>
        <owner>user</owner>
        <group>group</group>
        <permissions>rw-r--r--</permissions>
        <executable>false</executable>
        <hidden>false</hidden>
        <readonly>false</readonly>
        <iscollection>false</iscollection>
        <isfolder>false</isfolder>
        <isfile>true</isfile>
        <lastaccessed>Mon, 12 Aug 2025 09:00:00 GMT</lastaccessed>
        <lastmodifiedby>user</lastmodifiedby>
        <createdby>user</createdby>
        <size>12345</size>
        <contentlanguage>zh-CN</contentlanguage>
        <contentencoding>utf-8</contentencoding>
        <checksum>md5:abc123</checksum>
        <version>1.0</version>
        <comment>示例文件</comment>
        <!-- 其他属性 -->
      </prop>
      <status>HTTP/1.1 200 OK</status>
    </propstat>
  </response>
  <!-- 可包含多个 response 节点，表示多个资源 -->
</multistatus>
```

### 字段及含义

- `multistatus`：根元素，表示多资源状态响应。
    - `xmlns`：命名空间，通常为 DAV:。
- `response`：每个资源的响应信息。
    - `href`：资源路径。
    - `propstat`：属性状态块。
        - `prop`：实际的属性值。
            - `displayname`：资源显示名称。
            - `getcontentlength`：资源内容长度（字节）。
            - `getlastmodified`：资源最后修改时间。
            - `creationdate`：资源创建时间。
            - `resourcetype`：资源类型（如 collection 表示目录，文件为空）。
            - `getetag`：资源的实体标签（ETag）。
            - `getcontenttype`：资源内容类型（MIME 类型）。
            - `lockdiscovery`：锁信息。
            - `supportedlock`：支持的锁类型。
            - `source`：资源的源信息。
            - `quota-available-bytes`：可用配额（字节）。
            - `quota-used-bytes`：已用配额（字节）。
            - `owner`：资源所有者。
            - `group`：资源所属组。
            - `permissions`：权限信息。
            - `executable`：是否可执行。
            - `hidden`：是否隐藏。
            - `readonly`：是否只读。
            - `iscollection`：是否为集合（目录）。
            - `isfolder`：是否为文件夹。
            - `isfile`：是否为文件。
            - `lastaccessed`：最后访问时间。
            - `lastmodifiedby`：最后修改者。
            - `createdby`：创建者。
            - `size`：资源大小。
            - `contentlanguage`：内容语言。
            - `contentencoding`：内容编码。
            - `checksum`：内容校验值。
            - `version`：版本信息。
            - `comment`：备注。
            - 其他自定义属性。
        - `status`：HTTP 状态码（如 HTTP/1.1 200 OK）。

## 说明

- PROPFIND 方法可用于查询单个或多个资源的属性。
- 请求体可为空，表示查询所有默认属性。
- 返回体可包含多个 `response` 节点，分别对应不同资源。
- 属性字段可根据实际需求扩展，服务端应尽量支持标准属性。
