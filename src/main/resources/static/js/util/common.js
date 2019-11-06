var common={
    webPath:"https://www.buildermall.com/web/",
    en:{

    },
    key:{
        discountCodeMode:{
            1:"discount_rate"
        },
        orderState:{
            1:'unpaid',
            2:'unshipped',
            3:'unReceiv',
            4:'completed',
            5:'cancelled',
            6:'pending_refund',
            7:'refunded',
            8:'unquoted'
        },
        orderPaymentStatus:{
            1:'unpaid',
            2:'paid',
        },
        orderRefundState:{
            1:'application',
            2:'agreed',
            3:'rejected',
        },
        orderExpressCompany:{
            1:'debon_pca',
            2:'debon_pc',
            3:'shunfeng_pca',
            4:'shunfeng_pc'
        },
        orderPaymentMethod:{
            1:'bank_transfer',
            2:'online_payment'
        },
        orderOperatorType:{
            1:'admin',
            2:'supplier',
            3:'customers',
            4:'system'
        },
        orderRecordType:{
            1:'successful_order',
            2:'successful_payment',
            3:'order_shipment',
            4:'confirm_receipt',
            5:'cancellation_order',
            6:'apply_for_refund',
            7:'gree_to_refund',
            8:'refusal_of_refund',
            100:'modified_order_information'
        },
        deslAState:{
            1:'transaction',
            2:'successful_trade',
            3:'transaction_failure',
            4:'transaction_failure_1',
            5:'transaction_failure_2',
            6:'transaction_failure_3',
        },
        temProductState:{
            1:"unaudited",
            2:"audit_pass",
            3:"audit_failure"
        }
    },
    cn:{
        orderRefundState:{
            1:"申請中",
            2:"已同意",
            3:"已拒絕"
        },
        orderAddrType:{
            1:'賬單地址',
            2:'收貨地址'
        },
        orderPaymentStatus:{
            1:'未付款',
            2:'已付款',
            3:'已退款'
        },
        orderState:{
            1:'待付款',
            2:'待發貨',
            3:'待收貨',
            4:'已完成',
            5:'已取消',
            6:'待退款',
            7:'已退款',
            8:'待報價'
        },
        orderPaymentMethod:{
            1:'銀行轉賬',
            2:'線上支付'
        },
        orderExpressCompany:{
            1:'德邦快遞',
            2:'德邦快遞',
            3:'順豐快遞',
            4:'順豐快遞'
        },
        orderRecordType:{
            1:'下單成功',
            2:'付款成功',
            3:'訂單發貨',
            4:'確認收貨',
            5:'取消訂單',
            6:'申請退款',
            7:'同意退款',
            8:'拒絕退款',
            100:'修改了訂單信息'

        },
        orderOperatorType:{
            1:'管理員',
            2:'供應商',
            3:'客戶'
        },
        deslAState:{
            1:'交易中',
            2:'交易成功',
            3:'交易失敗',
            4:'交易失敗,交易金額或幣種與實際不匹配!'
        },

    },
    cnn:{

    },
    lang:{
        cn:{
           /* permission*/
            description:'描述',
            description_Chinese:'描述(中文)',
            description_English:'描述(英文)',
            name_Chinese:"名稱(中文)",
            name_English:"名稱(英文)",
            access_address:"訪問地址",
            sort:"排序",
            icon_style:"图标样式",
            submit:"提交",
            reset:"重置",
            add_permission:"添加權限",
            privilege_management:"權限管理",
            add_subprivileges:"添加子權限",
            module_name:'模塊名稱',
            module_path:'模塊路徑',
            icon:'图标',
            create_time:'创建时间',
            update_time:'更新时间',
            operation:"操作",
            no_permission:"无权限",
            page_determine:"确定",
            permission_list:"权限列表",
            /*  main*/
            zh_TW:'繁体',
            CH:'简体',
            EN:'英文',
            welcome:'欢迎光临',
            website_setup:"网站设置",
            personal_data:"个人资料",
            sign_out:"退出",
            home:"首页",
            skin_selection:"选择皮肤",
            fixed_slider:"固定滚动条",
            switch_to_the_left:"切换到坐左边",
            switch_narrow_screen:"切换窄屏",
            bottom_company_address:"中国广东省珠海市香洲区吉大九洲大道东石花三巷8号南山工业区3栋5楼507",
          /*  admin*/
            admin_name:"管理员名称",
            adding_time:"添加时间",
            query:"查询",
            add_admin:"添加管理员",
            administrator_Category_List:"管理员分类列表",
            all_administrators:"全部管理员",
            total:"共",
            persons:"人",
            role_name:"角色名称",
            login_name:"登录账号",
            real_name:"姓名",
            gender:"性别",
            phone:"电话",
            email:"邮箱",
            address:"地址",
            state:"状态",
            prohibit:"禁用",
            enable:"启用",
            supplier:"供应商",
            administrators:"管理员",
            type:"类型",
            secrecy:"保密",
            female:"女",
            male:"男",
            password:"密碼",
            password_remarks:"请填写6到12位密码",
            Please_select_a_role:"請選擇一個角色",
            role:"角色",
            editorial_administrator:"編輯管理員",
            add_administrator:"添加管理員",
            administrator_type:"管理员类型",
            /*Role*/
            add_role:'添加角色',
            residual_word_number:"剩余字数",
            word:"字",
            save_and_submit:"保存并提交",
            go_back:"返回上一步",
            cancel:"取消",
            permission_assignment:"权限分配",
            name:"名称",
            disable_enable:"禁用|启用",
           /*admin info*/
            administrator_information:'管理員信息',
            change_password:'修改密碼',
            register_time:"註冊時間",
            modify_info:"修改信息",
            save:"保存",
            original_password:"原&nbsp;&nbsp;密&nbsp;码",
            new_password:"新&nbsp;&nbsp;密&nbsp;码",
            confirm_password:"確認密碼",
            content:"內容",
            login_address:"登錄地點",
            client_IP:"客戶端ip",
            login_time:"登錄時間",
            login_successfully:"登錄成功",
            login_failed:"登錄失敗",
            /*user*/
            name_phone_email:"會員名稱、電話、郵箱",
            user_info_default:"这家伙很懒，什么也没有留下",
            password_describe:"请填写6到12位密码",
            receiving_address:"收貨地址",
            add_receiving_address:"添加收貨地址",
            company:"公司",
            detailed_address:"詳細地址",
            remarks:"備註",
            region:"地區",
           /* discountCode*/
            discount_code:"優惠碼",
            add_discountCode:"添加優惠碼",
            edit_discountCode:"編輯優惠碼",
            add_time:"添加時間",
            mode:"方式",
            effective_degree:"有效次數",
            unlimited:"不限",
            discount_rate:"折扣方式",
            discount:"折",
            prompt_box:"提示框",
           /* order*/
            proportion_of_shopping_products:"购物产品比例",
            show:"顯示",
            hide:"隱藏",
            order_status:"訂單狀態",
            all_order:"全部訂單",
            unpaid:"待付款",
            unshipped:"待發貨",
            unReceiv:'待收貨',
            completed:"已完成",
            cancelled:"已取消",
            pending_refund:"待退款",
            refunded:"已退款",
            unquoted:"待報價",
            order_no:"訂單編號",
            express_company:"快遞公司",
            choose_express:"--选择快递--",
            debang_express:"德邦快遞",
            debon_pca:"德邦快遞-精准卡航",
            debon_pc:"德邦快遞-精准汽运",
            shunfeng_pca:"順豐快遞-順豐標快",
            shunfeng_pc:"順豐快遞-順豐特惠",
            express_number:"快遞號",
            deliver_goods:"發貨",
            product:"產品",
            preferential_amount:"優惠金額",
            payment_amount:"付款金額",
            payment_status:"付款狀態",
            paid:"已付款",
            abnormal_state:"状态异常",
            explain:"说明",
            shipping_reminder:"发货提醒",
            shipping_reminder_msg:"電郵已發送至供應商郵箱",
            shipping:"发货",
            view_details:"查看",
            edit:"编辑",
            delete:"删除",
            order_info:"订单信息",
            whether_to_invoice_or_not:"是否開發票",
            yes:"是",
            no:"否",
            invoice_title:"发票抬头",
            taxpayer_identification_number:"纳税人识别号",
            recipient_Info:"收件人信息",
            product_info:"产品信息",
            payment_method:"支付方式",
            sum:"总数",
            total_price:"总价",
            no_logistics_information:"暂无物流信息!",
            order_operational_record:"订单操作记录",
            transaction_record:"交易记录",
            logistics_info:"物流信息",
            express_number_cannot_be_empty:"快遞號不能為空!",
            edit_order:"編輯訂單",
            preferential_amount:"優惠金額",
            please_select_the_order_status:"请选择訂單狀態",
            please_choose_payment_status:"請選擇付款狀態.",
            refund_status:"退款狀態",
            application:"申請中",
            agreed:"已同意",
            rejected:"已拒絕",
            all:"全部",
            transaction_amount:"交易金額",
            refund_amount:"退款金額",
            status:"狀態",
            refuse:"拒絕",
            reasons_for_refusal:"拒絕原因",
            refusal_of_refund:"拒絕退款",
            agree_to_refund:"同意退款",
            amount:"金額",
            prompt_info:"提示信息",
            agree:"同意",
            QTY:"数量",
            price:"单价",
            freight_charges:"运费",
            bank_transfer:"銀行過數",
            online_payment:"線上支付",
            admin:"管理員",
            supplier:"供應商",
            customers:"客戶",
            successful_order:"下單成功",
            successful_payment:"付款成功",
            order_shipment:"訂單發貨",
            confirm_receipt:"確認收貨",
            cancellation_order:"取消訂單",
            apply_for_refund:"申請退款",
            gree_to_refund:"同意退款",
            refusal_of_refund:"拒絕退款",
            modified_order_information:"修改了訂單信息",
            operation_instructions:"操作說明",
            transaction_number:"交易號",
            transaction:"交易中",
            successful_trade:"交易成功",
            transaction_failure:"交易失敗",
            transaction_failure_1:"交易失败，交易金额或货币与实际不符！",
            transaction_failure_2:"交易超时",
            transaction_failure_3:"交易异常,此笔交易异常,請手动处理",
          /*  product*/
            product_name:"產品名稱",
            type_list:"類型列表",
            number:"编号",
            brand:"品牌",
            title:"标题",
            original_price:"原价范围",
            discount_price:"折扣范围",
            describe:"描述",
            lower_shelf:"下架",
            upper_shelf:"上架",
            lower_upper:"下架|上架",
            choosing_product_types:"選擇產品類型",
            add_product:"添加產品",
            essential_information:"基本信息",
            detailed_information:"詳細信息",
            picture_information:"圖片信息",
            video_information:"影片信息",
            product_catalog:"產品目錄",
            product_specification:"產品規格",
            title_chinese:"標題(中文)",
            title_english:"標題(英文)",
            delivery_area:"發貨地區",
            receiving_area:"收貨地區",
            material_chinese:"材質(中文)",
            material_english:"材質(英文)",
            home_price:"首頁價格",
            material:"材質",
            features:"特點",
            brand_choice:"品牌選擇",
            brand_choice_tips:"选择或搜索品牌",
            unit_tips:"請選擇單位",
            piece:"件",
            kilogram:"千克",
            ton:"吨",
            set:"套",
            box:"箱",
            unit:"單位",
            min_order_number:"最低訂購數量",
            free_shipping:"是否包郵",
            system:"系统",
            unaudited:"待审核",
            audit_pass:"审核通过",
            audit_failure:"审核失败",
            picture:"圖片",
            audit_status:"審核狀態",
            video:"影片",
            submit_time:"提交時間",

          /*  home*/
            welcome_to_use:"欢迎使用 ",
            background_anagement_system:"后台管理系统",
            you_login_time:", 您本次登录时间为",
            mall_user:"商城用户",
            mall_suppliers:"商城供应商",
            mall_order:"商城订单",
            transaction_record:"交易记录",
            order_statistics:"订单统计信息",
            unpaid_order:"待付款订单：",
            unshipped_order:"待發貨订单：",
            unReceiv_order:'待收貨订单：',
            completed_order:"已成交订单：",
            failed_order:"交易失败订单：",
            pending_refund_order:"待退款订单：",
            commodity_statistics:"商品统计信息",
            total_commodity:"商品总数：",
            total_sold:"售出次数：",
            itemUppershelf:"上架商品：",
            itemDownshelf:"下架商品：",
            commodity_review:"商品评论：",
            user_login_statistics:"用戶登录统计信息",
            member_login:"会员登录：",
            admin_login:"管理員登录：",
            vendor_login:"供應商登录：",
            latest_news:"最新消息",
            add_merchandise:"添加商品",
            product_classification:"产品分类",
            personal_info:"个人信息",
            merchandise_order:"商品订单",
            lowest_price:"最低價格",

            /*brand*/
            brand_proportion:"国内外产品比例",
            domestic_brand:'国内品牌',
            foreign_brands:'国外品牌',
            quantity_abroad:"品牌数量",
            country:"国家",
            add_brand:"添加品牌",
            edit_brand:"编辑品牌",
            upload_icon:"上传图标",
            re_upload:"重新上传",
            add_productType:"添加产品类型",
            adding_subproduct_types:"添加子产品类型",
            product_list:'产品类型列表',
            /*system*/
            web_name:'网站名称',
            notification_mailbox:"系統郵箱",
            notice_amount:"交易金額通知",
            basic_settings:"基本設置",
        },
        en:{
            /* permission*/
            description:'Description',
            description_Chinese:'Describe(Ch)',
            description_English:'Describe(En)',
            name_Chinese:"Name(CH)",
            name_English:"Name(EN)",
            access_address:"Access address",
            sort:"Sort",
            icon_style:"Icon Style",
            submit:"Submit",
            reset:"Reset",
            add_permission:"Add Permission",
            privilege_management:"Privilege Management",
            add_subprivileges:"Add Subprivileges",
            module_name:'Module Name',
            module_path:'Module Path',
            icon:'Icon',
            create_time:'Create Time',
            update_time:'Update Time',
            operation:"Operation",
            no_permission:"No Permission",
            page_determine:"Determine",
            permission_list:"Permission list",
            prompt_box:"prompt box",
                /*main*/
            zh_TW:'zh_TW',
            CH:'CH',
            EN:'EN',
            welcome:'Welcome',
            website_setup:"Website setup",
            personal_data:"Personal data",
            sign_out:"Sign out",
            home:"Home",
            skin_selection:"Skin Selection",
            fixed_slider:"Fixed slider",
            switch_to_the_left:"Switch to the left",
            switch_narrow_screen:"Switch narrow screen",
            bottom_company_address:"Room13, 23/F, New Tech Plaza, 34 Tai Yau Street, San Po Kong, Kowloon, Hong Kong",
            /*  admin*/
            admin_name:"admin name",
            adding_time:"Adding time",
            query:"query",
            add_admin:"add admin",
            administrator_Category_List:"dministrator Category",
            all_administrators:"All Administrators",
            total:"total",
            persons:"persons",
            role_name:"Role name",
            login_name:"login name",
            real_name:"real name",
            gender:"gender",
            phone:"phone",
            email:"email",
            address:"address",
            state:"state",
            prohibit:"prohibit",
            enable:"enable",
            supplier:"supplier",
            administrators:"administrators",
            type:"type",
            secrecy:"secrecy",
            female:"female",
            male:"male",
            password:"password",
            password_remarks:"Please fill in the 6 to 12-digit password.",
            Please_select_a_role:"Please select a role",
            role:"role",
            editorial_administrator:"Editorial Administrator",
            add_administrator:"Add Administrator",
            administrator_type:"Administrator type",
            /*Role*/
            add_role:'Add Role',
            residual_word_number:"Residual word number",
            word:"word",
            save_and_submit:"Save and submit",
            go_back:"go back",
            cancel:"Cancel",
            permission_assignment:"Permission assignment",
            name:"name",
            disable_enable:"Disable | Enable",
            /*admin info*/
            administrator_information:'Administrator Information',
            change_password:'Change Password',
            register_time:"Register time",
            modify_info:"Modify Info",
            save:"save",
            administrator_login_record:'Administrator login record',
            original_password:"Original password",
            new_password:"New password",
            confirm_password:"Confirm password",
            content:"content",
            login_address:"Login address",
            client_IP:"Client IP",
            login_time:"Login time",
            login_successfully:"Login successfully",
            login_failed:"Login failed",
            /*user*/
            name_phone_email:"name phone email",
            user_info_default:"This fellow is lazy and leaves nothing behind.",
            password_describe:"Please fill in the 6 to 12-digit password.",
            receiving_address:"Receiving address",
            add_receiving_address:"Add Receiving address",
            company:"company",
            detailed_address:"Detailed address",
            remarks:"Remarks",
            region:"region",
            discount_code:"Discount code",
            add_discountCode:"Add DiscountCode",
            edit_discountCode:"Edit DiscountCode",
            add_time:"Add time",
            preferential_way:'Preferential way',
            mode:"mode",
            effective_degree:"effective degree",
            unlimited:"Unlimited",
            discount_rate:"Discount rate",
            discount:"discount",
            prompt_box:"prompt box",
            /*order*/
            proportion_of_shopping_products:"Proportion of shopping products",
            show:"show",
            hide:"hide",
            order_status:"Order status",
            all_order:"All Order",
            unpaid:"Unpaid",
            unshipped:"Unshipped",
            unReceiv:'UnReceiv',
            completed:"Completed",
            cancelled:"Cancelled",
            pending_refund:"Refunding",
            refunded:"Refunded",
            unquoted:"Unquoted",
            order_no:"Order No",
            express_company:"Express company",
            choose_express:"--Choose express--",
            debang_express:"Debang Express",
            debon_pca:"Debon Express - Precision Cargo Airlines",
            debon_pc:"Debon Express - Precision Carriage",
            shunfeng_pca:"Shunfeng Express - Shunfengbiao Express",
            shunfeng_pc:"Shunfeng Express - Shunfeng Special",
            express_number:"Express number",
            deliver_goods:"Deliver Goods",
            product:"product",
            preferential_amount:"Preferential amount",
            payment_amount:"Payment Amount",
            payment_status:"Payment status",
            paid:"Paid",
            abnormal_state:"Abnormal state",
            explain:"Explain",
            shipping_reminder:"Shipping reminder",
            shipping_reminder_msg:"E-mail has been sent to the supplier's mailbox",
            shipping:"Shipping",
            view_details:"Details",
            edit:"Edit",
            delete:"Delete",
            order_info:"Order info",
            customer_info:"Customer info",
            whether_to_invoice_or_not:"Whether to invoice or not",
            yes:"yes",
            no:"no",
            invoice_title:"Invoice title",
            taxpayer_identification_number:"Taxpayer identification number",
            recipient_Info:"Recipient Info",
            product_info:"Product info",
            payment_method:"Payment method",
            sum:"sum",
            total_price:"Total price",
            no_logistics_information:"No Logistics Information!",
            order_operational_record:"Order Operational Record",
            transaction_record:"Transaction record",
            logistics_info:"Logistics info",
            express_number_cannot_be_empty:"Express number cannot be empty!",
            edit_order:"edit order",
            preferential_amount:"Preferential amount",
            please_select_the_order_status:"Please select the order status",
            please_choose_payment_status:"Please choose payment status.",
            refund_status:"Refund status",
            application:"Application",
            agreed:"Agreed",
            rejected:"Rejected",
            all:"All",
            transaction_amount:"Transaction amount",
            refund_amount:"Refund amount",
            status:"status",
            refuse:"Refuse",
            reasons_for_refusal:"Reasons for refusal",
            refusal_of_refund:"Refusal of refund",
            agree_to_refund:"agree to refund",
            amount:"Amount",
            prompt_info:"Prompt info",
            agree:"agree",
            QTY:"QTY",
            home_price:"Home price",
            price:"price",
            freight_charges:"Freight charges",
            bank_transfer:"Bank Transfer",
            online_payment:"Online Payment",
            admin:"admin",
            supplier:"supplier",
            customers:"customers",
            successful_order:"Successful order",
            successful_payment:"Successful payment",
            order_shipment:"Order shipment",
            confirm_receipt:"Confirm receipt",
            cancellation_order:"Cancellation order",
            apply_for_refund:"apply for refund",
            gree_to_refund:"gree to refund",
            refusal_of_refund:"refusal of refund",
            modified_order_information:"Modified order information",
            transaction:"Transaction",
            successful_trade:"Successful trade",
            transaction_failure:"Transaction failure",
            transaction_failure_1:"The transaction failed, and the amount or currency of the transaction did not match the reality!",
            transaction_failure_2:"Transaction timeout",
            transaction_failure_3:"Exceptional transaction. This transaction is exceptional. Please handle it manually.",

            operation_instructions:"Operation instructions",
            transaction_number:"Transaction number",
            product_name:"Product Name",
            type_list:"Type list",
            number:"Number",
            brand:"Brand",
            title:"Title",
            original_price:"Original price",
            discount_price:"Discount price",
            describe:"Describe",
            lower_shelf:"Lower shelf",
            upper_shelf:"Upper shelf",
            lower_upper:"Lower shelf|Upper shelf",
            choosing_product_types:"Choosing Product Types",
            add_product:"add product",
            essential_information:"Essential Info",
            detailed_information:"Detailed Info",
            picture_information:"Picture Info",
            video_information:"Video Info",
            product_catalog:"Product Catalog",
            product_specification:"Product Specification",
            title_chinese:"Title(CH)",
            title_english:"Title(EH)",
            delivery_area:"Delivery Area",
            receiving_area:"Receiving Area",
            material_chinese:"Material (CH)",
            material_english:"Material (EN)",
            material:"Material",
            features:"Features",
            brand_choice:"Brand Choice",
            brand_choice_tips:"Choosing or Searching Brands",
            unit_tips:"Please select the unit.",
            piece:"piece",
            kilogram:"kg",
            ton:"ton",
            set:"set",
            box:"box",
            unit:"unit",
            min_order_number:"Min Order Number",
            free_shipping:"Free Shipping",
            system:"system",
            unaudited:"Unaudited",
            audit_pass:"Audit pass",
            audit_failure:"Audit failure",
            picture:"Picture",
            audit_status:"Audit Status",
            video:"video",
            submit_time:"Submit time",

             /*  home*/
            welcome_to_use:"welcome to use ",
            background_anagement_system:"Background Management System",
            you_login_time:", Your login time is",
            mall_user:"Mall user",
            mall_suppliers:"Mall suppliers",
            mall_order:"Mall order",
            transaction_record:"Transaction record",
            order_statistics:"Order statistics",
            unpaid_order:"Unpaid order:",
            unshipped_order:"Unshipped order:",
            unReceiv_order:'UnReceiv order:',
            completed_order:"Completed order:",
            failed_order:"Failed order:",
            pending_refund_order:"Pending refund:",
            commodity_statistics:"Commodity statistics",
            total_commodity:"Total commodity:",
            total_sold:"Times of sale:",
            itemUppershelf:"itemUppershelf:",
            itemDownshelf:"itemDownshelf:",
            commodity_review:"Reviews:",
            user_login_statistics:"User login statistics",
            member_login:"Member login:",
            admin_login:"Admin login:",
            vendor_login:"Vendor login:",
            latest_news:"Latest news",
            add_merchandise:"Add merchandise",
            product_classification:"Product classification",
            personal_info:"Personal info",
            merchandise_order:"Merchandise order",
            lowest_price:"Lowest price",

            /*brand*/
            brand_proportion:"Brand proportion",
            domestic_brand:'Domestic brand',
            foreign_brands:'Foreign brands',
            quantity_abroad:"Quantity abroad",
            state:"State",
            add_brand:"Add Brand",
            edit_brand:"Edit Brand",
            upload_icon:"Upload Icon",
            re_upload:"Re Upload",
            add_productType:"Adding Product Types",
            adding_subproduct_types:"Adding Subproduct Types",
            product_list:'List of Product Types',
            /*system*/
            web_name:'Web Name',
            notification_mailbox:"Notification mailbox",
            notice_amount:"Notification of amount",
            basic_settings:"Basic Settings",
        }

    },
    initLang:function () {

        $(".lang").each(function () {
            langHandle(this);
        })
        $("iframe").contents().find(".lang").each(function () {
            langHandle(this);
        })
        $("iframe").contents().find("iframe").contents().find(".lang").each(function () {
            langHandle(this);
        })
        $("iframe").contents().find("iframe").contents().find("iframe").contents().find(".lang").each(function () {
            langHandle(this);
        })
        parent.$(".lang").each(function () {
            langHandle(this);
        })


    },
    getDataByKey:function (key) {
        var lang = window.localStorage.getItem("lang");
        var value;
        switch (lang) {
            case 'cn':
                if(key.indexOf(";")!=-1){
                    value=Traditionalized(key.split(";")[0]);
                }else {
                    value=Traditionalized(common.lang[lang][key]);
                }
                break;
            case 'cnn':
                if(key.indexOf(";")!=-1){
                    value=Simplized(key.split(";")[0]);
                }else {
                    value=Simplized(common.lang.cn[key]);
                }
                break;
            case 'en':
                if(key.indexOf(";")!=-1){
                    var str = key.split(";");
                    value=str.length>1?str[1]:"";
                }else {
                    value=common.lang[lang][key];
                }
                break;
            default:{
                if(key.indexOf(";")!=-1){
                    value=Simplized(key.split(";")[0]);
                }else {
                    value=Simplized(common.lang.cn[key]);
                }
                break;
            }
        }
        return value;
    }
}
function JTPYStr()
{
    return '皑蔼碍爱翱袄奥坝罢摆败颁办绊帮绑镑谤剥饱宝报鲍辈贝钡狈备惫绷笔毕毙闭边编贬变辩辫鳖瘪濒滨宾摈饼拨钵铂驳卜补参蚕残惭惨灿苍舱仓沧厕侧册测层诧搀掺蝉馋谗缠铲产阐颤场尝长偿肠厂畅钞车彻尘陈衬撑称惩诚骋痴迟驰耻齿炽冲虫宠畴踌筹绸丑橱厨锄雏础储触处传疮闯创锤纯绰辞词赐聪葱囱从丛凑窜错达带贷担单郸掸胆惮诞弹当挡党荡档捣岛祷导盗灯邓敌涤递缔点垫电淀钓调迭谍叠钉顶锭订东动栋冻斗犊独读赌镀锻断缎兑队对吨顿钝夺鹅额讹恶饿儿尔饵贰发罚阀珐矾钒烦范贩饭访纺飞废费纷坟奋愤粪丰枫锋风疯冯缝讽凤肤辐抚辅赋复负讣妇缚该钙盖干赶秆赣冈刚钢纲岗皋镐搁鸽阁铬个给龚宫巩贡钩沟构购够蛊顾剐关观馆惯贯广规硅归龟闺轨诡柜贵刽辊滚锅国过骇韩汉阂鹤贺横轰鸿红后壶护沪户哗华画划话怀坏欢环还缓换唤痪焕涣黄谎挥辉毁贿秽会烩汇讳诲绘荤浑伙获货祸击机积饥讥鸡绩缉极辑级挤几蓟剂济计记际继纪夹荚颊贾钾价驾歼监坚笺间艰缄茧检碱硷拣捡简俭减荐槛鉴践贱见键舰剑饯渐溅涧浆蒋桨奖讲酱胶浇骄娇搅铰矫侥脚饺缴绞轿较秸阶节茎惊经颈静镜径痉竞净纠厩旧驹举据锯惧剧鹃绢杰洁结诫届紧锦仅谨进晋烬尽劲荆觉决诀绝钧军骏开凯颗壳课垦恳抠库裤夸块侩宽矿旷况亏岿窥馈溃扩阔蜡腊莱来赖蓝栏拦篮阑兰澜谰揽览懒缆烂滥捞劳涝乐镭垒类泪篱离里鲤礼丽厉励砾历沥隶俩联莲连镰怜涟帘敛脸链恋炼练粮凉两辆谅疗辽镣猎临邻鳞凛赁龄铃凌灵岭领馏刘龙聋咙笼垄拢陇楼娄搂篓芦卢颅庐炉掳卤虏鲁赂禄录陆驴吕铝侣屡缕虑滤绿峦挛孪滦乱抡轮伦仑沦纶论萝罗逻锣箩骡骆络妈玛码蚂马骂吗买麦卖迈脉瞒馒蛮满谩猫锚铆贸么霉没镁门闷们锰梦谜弥觅绵缅庙灭悯闽鸣铭谬谋亩钠纳难挠脑恼闹馁腻撵捻酿鸟聂啮镊镍柠狞宁拧泞钮纽脓浓农疟诺欧鸥殴呕沤盘庞国爱赔喷鹏骗飘频贫苹凭评泼颇扑铺朴谱脐齐骑岂启气弃讫牵扦钎铅迁签谦钱钳潜浅谴堑枪呛墙蔷强抢锹桥乔侨翘窍窃钦亲轻氢倾顷请庆琼穷趋区躯驱龋颧权劝却鹊让饶扰绕热韧认纫荣绒软锐闰润洒萨鳃赛伞丧骚扫涩杀纱筛晒闪陕赡缮伤赏烧绍赊摄慑设绅审婶肾渗声绳胜圣师狮湿诗尸时蚀实识驶势释饰视试寿兽枢输书赎属术树竖数帅双谁税顺说硕烁丝饲耸怂颂讼诵擞苏诉肃虽绥岁孙损笋缩琐锁獭挞抬摊贪瘫滩坛谭谈叹汤烫涛绦腾誊锑题体屉条贴铁厅听烃铜统头图涂团颓蜕脱鸵驮驼椭洼袜弯湾顽万网韦违围为潍维苇伟伪纬谓卫温闻纹稳问瓮挝蜗涡窝呜钨乌诬无芜吴坞雾务误锡牺袭习铣戏细虾辖峡侠狭厦锨鲜纤咸贤衔闲显险现献县馅羡宪线厢镶乡详响项萧销晓啸蝎协挟携胁谐写泻谢锌衅兴汹锈绣虚嘘须许绪续轩悬选癣绚学勋询寻驯训讯逊压鸦鸭哑亚讶阉烟盐严颜阎艳厌砚彦谚验鸯杨扬疡阳痒养样瑶摇尧遥窑谣药爷页业叶医铱颐遗仪彝蚁艺亿忆义诣议谊译异绎荫阴银饮樱婴鹰应缨莹萤营荧蝇颖哟拥佣痈踊咏涌优忧邮铀犹游诱舆鱼渔娱与屿语吁御狱誉预驭鸳渊辕园员圆缘远愿约跃钥岳粤悦阅云郧匀陨运蕴酝晕韵杂灾载攒暂赞赃脏凿枣灶责择则泽贼赠扎札轧铡闸诈斋债毡盏斩辗崭栈战绽张涨帐账胀赵蛰辙锗这贞针侦诊镇阵挣睁狰帧郑证织职执纸挚掷帜质钟终种肿众诌轴皱昼骤猪诸诛烛瞩嘱贮铸筑驻专砖转赚桩庄装妆壮状锥赘坠缀谆浊兹资渍踪综总纵邹诅组钻致钟么为只凶准启板里雳余链泄';
}
function FTPYStr()
{
    return '皚藹礙愛翺襖奧壩罷擺敗頒辦絆幫綁鎊謗剝飽寶報鮑輩貝鋇狽備憊繃筆畢斃閉邊編貶變辯辮鼈癟瀕濱賓擯餅撥缽鉑駁蔔補參蠶殘慚慘燦蒼艙倉滄廁側冊測層詫攙摻蟬饞讒纏鏟産闡顫場嘗長償腸廠暢鈔車徹塵陳襯撐稱懲誠騁癡遲馳恥齒熾沖蟲寵疇躊籌綢醜櫥廚鋤雛礎儲觸處傳瘡闖創錘純綽辭詞賜聰蔥囪從叢湊竄錯達帶貸擔單鄲撣膽憚誕彈當擋黨蕩檔搗島禱導盜燈鄧敵滌遞締點墊電澱釣調叠諜疊釘頂錠訂東動棟凍鬥犢獨讀賭鍍鍛斷緞兌隊對噸頓鈍奪鵝額訛惡餓兒爾餌貳發罰閥琺礬釩煩範販飯訪紡飛廢費紛墳奮憤糞豐楓鋒風瘋馮縫諷鳳膚輻撫輔賦複負訃婦縛該鈣蓋幹趕稈贛岡剛鋼綱崗臯鎬擱鴿閣鉻個給龔宮鞏貢鈎溝構購夠蠱顧剮關觀館慣貫廣規矽歸龜閨軌詭櫃貴劊輥滾鍋國過駭韓漢閡鶴賀橫轟鴻紅後壺護滬戶嘩華畫劃話懷壞歡環還緩換喚瘓煥渙黃謊揮輝毀賄穢會燴彙諱誨繪葷渾夥獲貨禍擊機積饑譏雞績緝極輯級擠幾薊劑濟計記際繼紀夾莢頰賈鉀價駕殲監堅箋間艱緘繭檢堿鹼揀撿簡儉減薦檻鑒踐賤見鍵艦劍餞漸濺澗漿蔣槳獎講醬膠澆驕嬌攪鉸矯僥腳餃繳絞轎較稭階節莖驚經頸靜鏡徑痙競淨糾廄舊駒舉據鋸懼劇鵑絹傑潔結誡屆緊錦僅謹進晉燼盡勁荊覺決訣絕鈞軍駿開凱顆殼課墾懇摳庫褲誇塊儈寬礦曠況虧巋窺饋潰擴闊蠟臘萊來賴藍欄攔籃闌蘭瀾讕攬覽懶纜爛濫撈勞澇樂鐳壘類淚籬離裏鯉禮麗厲勵礫曆瀝隸倆聯蓮連鐮憐漣簾斂臉鏈戀煉練糧涼兩輛諒療遼鐐獵臨鄰鱗凜賃齡鈴淩靈嶺領餾劉龍聾嚨籠壟攏隴樓婁摟簍蘆盧顱廬爐擄鹵虜魯賂祿錄陸驢呂鋁侶屢縷慮濾綠巒攣孿灤亂掄輪倫侖淪綸論蘿羅邏鑼籮騾駱絡媽瑪碼螞馬罵嗎買麥賣邁脈瞞饅蠻滿謾貓錨鉚貿麽黴沒鎂門悶們錳夢謎彌覓綿緬廟滅憫閩鳴銘謬謀畝鈉納難撓腦惱鬧餒膩攆撚釀鳥聶齧鑷鎳檸獰甯擰濘鈕紐膿濃農瘧諾歐鷗毆嘔漚盤龐國愛賠噴鵬騙飄頻貧蘋憑評潑頗撲鋪樸譜臍齊騎豈啓氣棄訖牽扡釺鉛遷簽謙錢鉗潛淺譴塹槍嗆牆薔強搶鍬橋喬僑翹竅竊欽親輕氫傾頃請慶瓊窮趨區軀驅齲顴權勸卻鵲讓饒擾繞熱韌認紉榮絨軟銳閏潤灑薩鰓賽傘喪騷掃澀殺紗篩曬閃陝贍繕傷賞燒紹賒攝懾設紳審嬸腎滲聲繩勝聖師獅濕詩屍時蝕實識駛勢釋飾視試壽獸樞輸書贖屬術樹豎數帥雙誰稅順說碩爍絲飼聳慫頌訟誦擻蘇訴肅雖綏歲孫損筍縮瑣鎖獺撻擡攤貪癱灘壇譚談歎湯燙濤縧騰謄銻題體屜條貼鐵廳聽烴銅統頭圖塗團頹蛻脫鴕馱駝橢窪襪彎灣頑萬網韋違圍爲濰維葦偉僞緯謂衛溫聞紋穩問甕撾蝸渦窩嗚鎢烏誣無蕪吳塢霧務誤錫犧襲習銑戲細蝦轄峽俠狹廈鍁鮮纖鹹賢銜閑顯險現獻縣餡羨憲線廂鑲鄉詳響項蕭銷曉嘯蠍協挾攜脅諧寫瀉謝鋅釁興洶鏽繡虛噓須許緒續軒懸選癬絢學勳詢尋馴訓訊遜壓鴉鴨啞亞訝閹煙鹽嚴顔閻豔厭硯彥諺驗鴦楊揚瘍陽癢養樣瑤搖堯遙窯謠藥爺頁業葉醫銥頤遺儀彜蟻藝億憶義詣議誼譯異繹蔭陰銀飲櫻嬰鷹應纓瑩螢營熒蠅穎喲擁傭癰踴詠湧優憂郵鈾猶遊誘輿魚漁娛與嶼語籲禦獄譽預馭鴛淵轅園員圓緣遠願約躍鑰嶽粵悅閱雲鄖勻隕運蘊醞暈韻雜災載攢暫贊贓髒鑿棗竈責擇則澤賊贈紮劄軋鍘閘詐齋債氈盞斬輾嶄棧戰綻張漲帳賬脹趙蟄轍鍺這貞針偵診鎮陣掙睜猙幀鄭證織職執紙摯擲幟質鍾終種腫衆謅軸皺晝驟豬諸誅燭矚囑貯鑄築駐專磚轉賺樁莊裝妝壯狀錐贅墜綴諄濁茲資漬蹤綜總縱鄒詛組鑽緻鐘麼為隻兇準啟闆裡靂餘鍊洩';
}
function langHandle(obj) {
    var lang = window.localStorage.getItem("lang");
    var key = $(obj).attr("key");
    if(!key)
        return;
    var value = "";
    switch (lang) {
        case 'cn':
            if(key.indexOf(";")!=-1){
                value=Traditionalized(key.split(";")[0]);
            }else {
                value=Traditionalized(common.lang[lang][key]);
            }
            break;
        case 'cnn':
            if(key.indexOf(";")!=-1){
                value=Simplized(key.split(";")[0]);
            }else {
                value=Simplized(common.lang.cn[key]);
            }
            break;
        case 'en':
            if(key.indexOf(";")!=-1){
                var str = key.split(";");
                value=str.length>1?str[1]:"";
            }else {
                value=common.lang[lang][key];
            }
           break;
        default:{
            if(key.indexOf(";")!=-1){
                value=Simplized(key.split(";")[0]);
            }else {
                value=Simplized(common.lang.cn[key]);
            }
            break;
        }
    }
    if(value&&value!="undefined"&&value!="null"){
        $(obj).html(value);
    }
}
function Traditionalized(cc){
    if(!cc)
        return;
    var str='',ss=JTPYStr(),tt=FTPYStr();
    for(var i=0;i<cc.length;i++)
    {
        if(cc.charCodeAt(i)>10000&&ss.indexOf(cc.charAt(i))!=-1)str+=tt.charAt(ss.indexOf(cc.charAt(i)));
        else str+=cc.charAt(i);
    }
    return str;
}
function Simplized(cc) {
    if(!cc)
        return;
    var str = '', ss = JTPYStr(), tt = FTPYStr();
    for (var i = 0; i < cc.length; i++) {
        if (cc.charCodeAt(i) > 10000 && tt.indexOf(cc.charAt(i)) != -1) str += ss.charAt(tt.indexOf(cc.charAt(i)));
        else str += cc.charAt(i);
    }
    return str;
}

$(document).ready(function() {
    var t1=window.setTimeout(function () {
        common.initLang();
        window.clearTimeout(t1);
    }, 100);
    $("#language").val(window.localStorage.getItem("lang"));
})
