/**
 * 获取一个月有多少天
 *
 * 已知月份，得到这个月的第一天和最后一天作为查询条件查范围内的数据
 *
 * new Date(year,month,fate,hrs,min,sec)
 *      new Date(2019, 12, 0).getDate(); //31
 *      new Date(2018, 2, 0).getDate(); //28
 *
 * @param month
 * @returns {number}
 */
function getMonthLength(month) {
    const date = new Date(month);
    let year = date.getFullYear();
    //月份是从 0 开始计算的
    let _month = date.getMonth() + 1;
    return new Date(year, _month, 0).getDate();
}


// 函数的length属性 就是函数参数的个数，函数的参数arguments 是一个类数组对象，有length属性

// 字位反转操作符 ~ 返回 2 的补码，~x 大致等同于 -(x+1) 例： ~42; // -(42+1) ===> -43

/**
 * 给定一组 url，利用 js 的异步实现并发请求，并按顺序输出结果
 *
 * @param url
 * @returns {Promise<any>}
 */
function getData(url) {
    // 返回一个 Promise 利用 Promise.all 接受
    return new Promise((resolve, reject) => {
        const xhr = new XMLHttpRequest();
        xhr.responseType = 'json';
        xhr.onreadystatechange = () => {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    resolve(xhr.response);
                }
            }
        };
        xhr.open('GET', url, true);
        xhr.send(null);
    });
}

const urls = ['./1.json', './2.json', './3.json'];
function getMultiData(urls) {
    // Promise.all 接受一个包含 promise 的数组，如果不是 promise 数组会被转成 promise
    Promise.all(urls.map(url => getData(url))).then(results => {
        console.log(results);
    });
}

/**
 * 字符串转 txt 文件（blob）
 * 实现原理:动态的创建一个可以下载的 a 标签，给它设置 download 属性，然后把下载的内容转 blob 创建下载链接下载即可
 *
 * @param text
 * @param filename
 */
function exportTxt(text, filename) {
    const eleLink = document.createElement('a');
    eleLink.download = filename;
    eleLink.style.display = 'none';
    // 将内容转为 blob
    const blob = new Blob([text]);
    eleLink.href = URL.createObjectURL(blob);
    document.body.appendChild(eleLink);
    eleLink.click();
    document.body.removeChild(eleLink);
}

/**
 * 格式化金钱
 * 999999999 格式化成999,999,999
 *
 * @param price
 * @returns {string}
 */
function formatPrice(price) {
    return String(price).replace(/\B(?=(\d{3})+(?!\d))/g, ',');

    /*return String(price)
        .split('')
        .reverse()
        .reduce((prev, next, index) => {
            return (index % 3 ? next : next + ',') + prev;
        });*/

    // (999999999).toLocaleString(); // 999,999,999

    /*const options = {
        style: 'currency',
        currency: 'CNY',
    };
    (123456).toLocaleString('zh-CN', options); // ¥123,456.00*/
}

/**
 * 深度冻结对象
 * 不想 vue 为他做双向绑定，以减少一些性能上消耗，我们可以把使用 Object.freeze 将对象冻结
 *
 * @param o
 * @returns {ReadonlyArray<any>}
 */
const deepFreeze = o => {
    const propNames = Object.getOwnPropertyNames(o);
    propNames.forEach(name => {
        const prop = o[name];
        if (typeof prop === 'object' && prop !== null) {
            deepFreeze(prop);
        }
    });
    return Object.freeze(o);
};

/**
 * 脱敏处理
 * 涉及到用户隐私情况下，可能会遇到对用户的手机号身份证号之类的信息脱敏，但是这个脱敏数据的规则是根据用户信息要脱敏字段动态的生成的，此时我们动态拼接正则来实现一个动态脱敏规则
 *
 * @param before
 * @param after
 * @returns {RegExp}
 */
const encryptReg = (before = 3, after = 4) => {
    return new RegExp('(\\d{' + before + '})\\d*(\\d{' + after + '})');
};
// 使用：'13456789876'.replace(encryptReg(), '$1****$2') -> "134****9876"