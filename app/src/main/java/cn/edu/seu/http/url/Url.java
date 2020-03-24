package cn.edu.seu.http.url;

public class Url {

    // 登录才操作    POST
    // param:   account,password
    // return:
    public final static String LOGIN_URL = "http://localhost:8000/user_login";


    // 注册操作 POST
    // param:   account,password,sex,address(住址),tel
    // return:
    public final static String REGIST_URL = "http://localhost:8000/user_regist";


    // 查看历史数据操作 post
    // param：   userid,uploadTime,permitVisit
    // return:
    public final static String HISTORY_DATA_URL = "http://localhost:8000/user_findHistoryData";


    // 历史数据详情   get
    // param：   dataAddr(数据在区块链上的地址)
    // return:
    public final static String HISTORY_DATA_DETAILS_URL = "http://localhost:8000/user_historyData_details";


    // 查看医疗服务详细信息操作 get
    // param:   id
    // return:
    public final static String MEDICAL_SERVICE_URL = "http://localhost:8000/user_findMedicalService";


    // 查找医疗机构操作 post
    // param:   organizationName
    // return:
    public final static String ORGANIZATION_URL = "http://localhost:8000/user_findOrganization";


    // 查看医疗机构详细信息操作    get
    // param:   id
    // return:
    public final static String ORGANIZATION_DETAILS_URL = "http://localhost:8000/user_organization_detail";


    // 上传数据操作   post
    // param:   userid, permitVisit(是否公开), healthyData(健康数据)
    // returm:
    public final static String UPLOAD_DATA_URL = "http://localhost:8000/user_uploadData";


    // 查看用户个人信息操作   get
    // param: id
    // return:
    public final static String USER_INFO_URL = "http://localhost:8000/user_myInfo";;


    // 修改用户信息操作     post
    // param:   id,password,sex,address(住址),tel
    // return:
    public final static String UPDATE_USER_INFO_URL = "http://localhost:8000/user_updateInfo";


    // 查看用户钱包信息操作   get
    // param:   id
    // return:
    public final static String USER_WALLET_URL = "http://localhost:8000/user_myWallet";

    // 转账操作     post
    // param:      sendAddress(付款方以太坊账号-->本人账号), recieveAddress(收款方以太坊账号), transactEth(交易金额)
    // return:
    public final static String USER_TRANSFER = "http://localhost:8000/user_transfer";


    // 查看个人交易记录     get
    // param:   ethAddress(本人以太坊地址)
    // return:
    public final static String USER_TRANSACTION_RECORD_URL = "http://localhost:8000/user_transactionRecord";


    // 购买以太币操作  post
    // param:   sendAddress(付款方以太坊账号-->管理员), recieveAddress(收款方以太坊账号-->本人账号), transactEth(交易金额)
    // return:
    public final static String BUY_ETH_COIN_URL = "http://localhost:8000/user_butEthCoin";


    // 查看访客记录操作 get
    // param:   id
    // return:
    public final static String VISIT_DATA_URL = "http://localhost:8000/user_visitData";;
}
