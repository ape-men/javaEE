
export class fetcher {

    constructor(IFieldName) {
        this.IField = "http://www.bluemsun.work:8080/"+IFieldName+"/";
    }
    
    getI(IName) {
        return this.IField + IName;
    }

    createFetch(url, body) {
        return fetch(url, body)
            .then((res) => res.json())
            .catch(() => {});
    }

    get(IName, params) {
        return this.createFetch(this.getI(IName) + this.createParamString(params), {
            method: "GET",
            credentials: 'include',
        });
    }

    createParamString(params) {
        let paramString = '', cnt = 0;
        for (let m in params) {
            paramString += (cnt++ ? '&' : '?') + m + '=' + params[m];
        }
        return paramString;
    }

    post(IName, body) {
        return this.createFetch(this.getI(IName), {
            method: "POST",
            credentials: 'include',
            body: this.createFormData(body),
        });
    }

    createFormData(data) {
        let formData = new FormData();
        for (let m in data) {
            formData.append(m, data[m]);
        }
        return formData;
    }
}

export class fetcherWithToken extends fetcher {

    createFetch(url, body) {
        body = body || {};    
        body.headers = Object.assign(body.headers || {}, {'token': localStorage.getItem('token')});
        console.log(body);
        return fetch(url, body)
            .then((res) => res.json())
            .then((res) => {
                if (!~res.success) {
                    location.href = "./login"
                }
                return res;
            })
            .catch(() => {});
    }
    
};
/*
let f = new class fetcher {
    constructor(url) {this.url = url}
    loopReqs(array) {
        for (let id of array) this.loopReq(id);
    }
    loopReq(id) {
        fetch(this.url+"/qzxk/xk/processXk", {
            method: "POST", 
            headers: {'Content-Type':'application/x-www-form-urlencoded'},
            body:"jx0502id=45&jx0404id="+id+"&jx0502zbid=103"
        }).then((res) => res.json()).then((res) => {
            if (res.flag) console.log("get"+id);
            else this.loopReq(id);
        })
    }
}();
*/