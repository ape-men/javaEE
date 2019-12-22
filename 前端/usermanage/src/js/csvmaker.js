export default new class CSVmaker {
    platform = {};
    constructor() {
        this.platform.isOnline = location.protocol.indexOf("http") == 0;
        ["Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod"].forEach(function (t) {
            if (navigator.userAgent.indexOf(t) >= 0) {
                if (t == 'iPhone' || t == 'iPad' || t == 'iPod') this.platform.isIOS = true;
                if (t == 'Android') this.platform.isAndroid = true;
                this.platform.isPC = false;
            }
        });
        this.platform.string = this.platform.isPC ? "PC" : this.platform.isAndroid ? "Android" : this.platform.isIOS ? "iOS" : "";
        this.platform.supportCopy = document.queryCommandSupported && document.queryCommandSupported("copy");
        var chrome = /Chrome\/(\d+)\./i.exec(navigator.userAgent);
        if (chrome && parseInt(chrome[1]) >= 50) this.platform.isChrome = true;
        this.platform.isSafari = /Safari/i.test(navigator.userAgent) && !/Chrome/i.test(navigator.userAgent);
        this.platform.isQQ = /QQ/i.test(navigator.userAgent);
        this.platform.isWeChat = /MicroMessenger/i.test(navigator.userAgent);
    }

    copy(data) {

        if (window.jsinterface) {
            window.jsinterface.copy(data);
            return true;
        }
    
        if (!this.platform.supportCopy) return false;
    
        var textArea = document.createElement("textarea");
        textArea.style.position = 'fixed';
        textArea.style.top = 0;
        textArea.style.left = 0;
        textArea.style.width = '2em';
        textArea.style.height = '2em';
        textArea.style.padding = 0;
        textArea.style.border = 'none';
        textArea.style.outline = 'none';
        textArea.style.boxShadow = 'none';
        textArea.style.background = 'transparent';
        textArea.value = data;
        document.body.appendChild(textArea);
        textArea.focus();
        textArea.setSelectionRange(0, textArea.value.length);
        var successful = false;
        try {
            successful = document.execCommand('copy');
        } catch (err) {
            successful = false;
        }
    
        document.body.removeChild(textArea);
        return successful;
    }
    

    makeCSV(data, opt) {
        let out = [];
        for (let row of data) {
            out.push(opt(row));
        }
        return out.join('\n');
    }

    download(filename, th, data, process) {
        console.log(data);
        var content = th+'\n'+this.makeCSV(data, process);
        if (window.jsinterface) {
            window.jsinterface.download(filename, content);
            return;
        }

        // Step 0: 不为http/https，直接不支持
        if (!this.platform.isOnline) {
            alert("离线状态下不支持下载操作！");
            return;
        }

        // Step 1: 如果是iOS平台，直接不支持
        if (this.platform.isIOS) {
            if (this.copy(content)) {
                alert("iOS平台下不支持直接下载文件！\n所有应下载内容已经复制到您的剪切板，请自行创建空白文件并粘贴。");
            }
            else {
                alert("iOS平台下不支持下载操作！");
            }
            return;
        }

        // Step 2: 如果不是PC平台（Android），则只支持chrome
        if (!this.platform.isPC) {
            if (!this.platform.isChrome || this.platform.isQQ || this.platform.isWeChat) { // 检测chrome
                if (core.copy(content)) {
                    alert("移动端只有Chrome浏览器支持直接下载文件！\n所有应下载内容已经复制到您的剪切板，请自行创建空白文件并粘贴。");
                }
                else {
                    alert("该平台或浏览器暂不支持下载操作！");
                }
                return;
            }
        }

        // Step 3: 如果是Safari浏览器，则提示并打开新窗口
        if (this.platform.isSafari) {
            alert("你当前使用的是Safari浏览器，不支持直接下载文件。\n即将打开一个新窗口为应下载内容，请自行全选复制然后创建空白文件并粘贴。");
            var blob = new Blob([content], {type: 'text/plain;charset=utf-8'});
            var href = window.URL.createObjectURL(blob);
            var opened = window.open(href, "_blank");
            window.URL.revokeObjectURL(href);
            return;
        }

        // Step 4: 下载
        var blob = new Blob([content], {type: 'text/plain;charset=utf-8'});
        if (window.navigator.msSaveOrOpenBlob) {
            window.navigator.msSaveBlob(blob, filename);
        }
        else {
            var href = window.URL.createObjectURL(blob);
            var elem = window.document.createElement('a');
            elem.href = href;
            elem.download = filename;
            document.body.appendChild(elem);
            elem.click();
            document.body.removeChild(elem);
            window.URL.revokeObjectURL(href);
        }
    }
}