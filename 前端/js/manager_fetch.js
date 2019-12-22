"use scrict";

var manager_fecth = new fetcher("superAdmin");

/**
 * 列出所有成员
 * @param {Number} pageNum 页码
 * @returns {{
 *      success: Number
 *      info: String
 *      userList: Array
 *      pageNum: Number
 *      pages: Number
 *      total: Number
 * }} 
 */
manager_fecth.listAllUser = function(pageNum) {
    return this.get("listAllUser", {pageNum});
}

manager_fecth.getLoginRecord = function() {
    return this.get("getLoginRecord");
}

/**
 * 列出所有成员
 * @param {Number} userId 页码
 * @param {Number} status 页码
 * @param {Number} available 页码
 * @returns {{success: Number info: String}} 
*/
manager_fecth.updateUserStatusAndAvailable = function(userId, status, available) {
    return this.get("updateUserStatusAndAvailable", {userId, status, available});
}
