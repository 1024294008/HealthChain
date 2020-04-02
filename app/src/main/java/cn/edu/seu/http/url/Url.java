package cn.edu.seu.http.url;

public class Url {

    public final static String IFCONFIG = "192.168.0.105:3000";

    // 登录才操作    POST
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
    public final static String HISTORY_DATA_URL = "http://" + IFCONFIG + "/api/user/getHealthData";


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
    public final static String MEDICAL_SERVICE_URL = "http://" + IFCONFIG + "/api/common/getMedicalServiceInfo";


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
    public final static String UPLOAD_DATA_URL = "http://" + IFCONFIG + "/user_uploadData";


    // 查看用户个人信息操作   get
    // param: id
    // return:
    public final static String USER_INFO_URL = "http://" + IFCONFIG + "/api/user/getUserInfo";;


    // 修改用户信息操作     post
    // param:   id,password,sex,address(住址),tel,nickName
    // return:  {"code": "200"} 200表示修改成功， 201表示修改失败
    public final static String UPDATE_USER_INFO_URL = "http://" + IFCONFIG + "/api/user/updateUserInfo";


    // 查看用户钱包信息操作   get    --  通过id拿到用户信息  根据以太坊地址查询交易记录
    // param:   id
    // return: {"ethAddress":"以太坊地址", "balance":"余额", "transactionRecordList":[{"id":"交易id", "transactTime":"交易时间", "transactEth": "交易金额", "transactAddr":"交易在区块链上的地址"}, ...]}
    public final static String USER_WALLET_URL = "http://" + IFCONFIG + "/user_myWallet";

    // 转账操作     post
    // param:      sendAddress(付款方以太坊账号-->本人账号), recieveAddress(收款方以太坊账号), transactEth(交易金额), transactRemarks(备注)
    // return: {"code": "200", "data": {}, "userBalance": "用户余额,后台通过web3直接查询"}
    public final static String USER_TRANSFER = "http://" + IFCONFIG + "/user_transfer";


    // 查看个人交易记录     get
    // param:   ethAddress(本人以太坊地址)
    // return: 暂无
    public final static String USER_TRANSACTION_RECORD_URL = "http://" + IFCONFIG + "/user_transactionRecord";


    // 购买以太币操作  post
    // param:   sendAddress(付款方以太坊账号-->管理员), recieveAddress(收款方以太坊账号-->本人账号), transactEth(交易金额)
    // return: 暂无
    public final static String BUY_ETH_COIN_URL = "http://" + IFCONFIG + "/user_butEthCoin";


    // 查看访客记录操作 get
    // param:   id
    // return: 暂无
    public final static String VISIT_DATA_URL = "http://" + IFCONFIG + "/user_visitData";;
}
