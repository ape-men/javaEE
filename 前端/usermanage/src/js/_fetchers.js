import {fetcher, fetcherWithToken} from "./fetcher";

export var loginFetcher = new fetcher("login");
export var passwordFetcher = new fetcher("password");
export var registerFetcher = new class register_fetcher extends fetcher {

    /**
     * 注册时判断用户名是否冲突
     * @param {String} userName 用户名
     * @returns {{success: Number info: String}} 
     */
    checkoutUserName(userName) {
        return this.get("checkoutUserName", {userName});
    }

    checkoutEmail(email) {
        return this.get("checkoutEmail", {email});
    }
}("register");

export var personPageFetcher = new fetcherWithToken("personPage");
export var superAdminFetcher = new fetcherWithToken("superAdmin");
