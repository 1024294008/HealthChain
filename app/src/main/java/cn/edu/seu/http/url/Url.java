package cn.edu.seu.http.url;

public class Url {

    public final static String IFCONFIG = "192.168.1.100:3000";

    // 登录才操作    POSTr
    // param:   account,password
    // return:  {用户信息... , "code": "200"}
    public final static String LOGIN_URL = "http://" + IFCONFIG + "/api/user/login";


    // 注册操作 POST
    // param:   account,password,address(住址),tel
    // return:  {"code": 200}   200 表示注册成功   201 表示存在相同的用户名, 202注册失败
    public final static String REGIST_URL = "http://" + IFCONFIG + "/api/user/register";


    // 查看历史数据操作 post
    // param：   userid
    // return: {"dataList": [{"time":"2018-1-8", "eval":"你好", "dataAddr":"0X8888"}, {"time":"", "eval":"", "dataAddr":""}, ...]}
    public final static String HISTORY_DATA_URL = "http://" + IFCONFIG + "/api/user/getHealthDataList";


    // 历史数据详情   get
    // param：   dataAddr(数据在区块链上的地址)
    // return:  暂无
    public final static String HISTORY_DATA_DETAILS_URL = "http://" + IFCONFIG + "/user_historyData_details";


    // 最近一条数据 post
    // param：   userid,
    // return: {"latestData": {"uploadTime":"2018-1-10","distance":"10000","heat":"76","sleepQuality":"good","heartRate":"75/s"}}
    public final static String LATEST_DATA_URL = "http://" + IFCONFIG + "/api/user/getHealthData";


    // 查看医疗服务详细信息操作 get
    // param:   id
    // return: 暂无
    public final static String MEDICAL_SERVICE_URL = "http://" + IFCONFIG + "/api/common/getServiceAndOrg";


    // 所有医疗服务页面 get
    // param:
    // return:  {"serviceList":[{"serviceId": "id", "serviceName": "xxx"}, {"serviceId": "id", "serviceName": "xxx"}, ...}]}
    public final static String ALL_MEDICAL_SERVICE_URL = "http://" + IFCONFIG + "/api/user/getServiceList";


    // 查找医疗机构操作 post
    // param:   organizationName
    // return:  暂无
    public final static String ORGANIZATION_URL = "http://" + IFCONFIG + "/user_findOrganization";


    // 查看医疗机构详细信息操作    get
    // param:   id
    // return: 暂无
    public final static String ORGANIZATION_DETAILS_URL = "http://" + IFCONFIG + "/api/common/getOrgInfo";


    // 上传数据操作   post
    // param:   userid, permitVisit(是否公开), distance, heat, sleepQuality, heartRate
    // return: {"code": "200", "msg": "上传成功"}
    public final static String UPLOAD_DATA_URL = "http://" + IFCONFIG + "/api/common/uploadUserHealthData";


    // 查看用户个人信息操作   get
    // param: id
    // return:
    public final static String USER_INFO_URL = "http://" + IFCONFIG + "/api/user/getUserInfo";;


    // 修改用户信息操作     post
    // param:   id,password,sex,address(住址),tel,nickName
    // return:  {"code": "200"} 200表示修改成功， 201表示修改失败
    public final static String UPDATE_USER_INFO_URL = "http://" + IFCONFIG + "/api/user/updateUserInfo";


    // 获取用户余额
    // param:   id
    // return:
    public final static String USER_BALANCE_URL = "http://" + IFCONFIG + "/api/user/getBalance";

    // 用户的转账记录
    public final static String USER_TRANSFER_ALL_URL = "http://" + IFCONFIG + "/api/user/findRecordByEthAddress";

    // 用户之间转账转账操作     post
    // param:      输入用户的账号
    // return: {"code": "200", "data": {}, "userBalance": "用户余额,后台通过web3直接查询"}
    public final static String USER_TRANSFER_TO_USER = "http://" + IFCONFIG + "/api/user/transferUserToUser";

    // 用户向机构转账
    public final static String USER_TRANSFER_TO_ORG = "http://" + IFCONFIG + "/api/user/transfer";

    // 交易记录详情
    public final static String TRANSACTION_RECORD_DETAIL = "http://" + IFCONFIG + "/api/user/UserTransactionRecordDetail";

    // 用户购买服务的交易记录
    public final static String USER_BUY_SERVICE_URL = "http://" + IFCONFIG + "/api/user/findBytransactRemarks";

    // 查看访客记录操作 post
    // param:   id
    // return: 暂无
    public final static String VISIT_DATA_URL = "http://" + IFCONFIG + "/api/user/findRecordAndOrnInfoByUserId";

    // 购买以太币
    public final static String BUY_ETH_COIN_URL = "http://" + IFCONFIG + "";

    // 用户转账
    public final static String USER_TRANSFER = "http://" + IFCONFIG + "";

    // 交易记录
    public final static String USER_TRANSACTION_RECORD_URL = "http://" + IFCONFIG + "";
}
