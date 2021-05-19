/**
 * 深拷贝 与 浅拷贝
 *
 * JavaScript 中两个数据类型：
 *      基本数据类型(Number、String、Boolean、Undefined、Null、Symbol)
 *      引用数据类型(Object、Array、Date、RegExp、Function)
 *      基本数据类型保存在栈内存中，
 *      引用数据类型保存在堆内存中，其变量是一个指向堆内存中实际对象的引用，存在栈中(栈内存中保存了变量标识符和指向堆内存中该对象的指针,堆内存中保存了对象的内容)
 *
 *  浅拷贝：指的是创建新的数据，这个数据有着原始数据属性值的一份精确拷贝。 基本类型，拷贝的就是基本类型的值。引用类型，拷贝的就是内存地址。
 *      Object.assign
 *      Array.prototype.slice()  Array.prototype.concat()
 *      使用拓展运算符实现的复制 ...
 *
 *  深拷贝：两个对象属性完成相同，但是对应两个不同的地址，修改一个对象的属性，不会改变另一个对象的属性
 *      _.cloneDeep()
 *      jQuery.extend()
 *      JSON.stringify()
 *      手写循环递归
 */

/**
 * 循环递归
 * @param obj
 * @param hash
 * @returns {*}
 */
function deepClone(obj, hash = new WeakMap()) {
  if (obj === null) return obj; // 如果是null或者undefined我就不进行拷贝操作
  if (obj instanceof Date) return new Date(obj);
  if (obj instanceof RegExp) return new RegExp(obj);
  // 可能是对象或者普通的值  如果是函数的话是不需要深拷贝
  if (typeof obj !== "object") return obj;
  // 是对象的话就要进行深拷贝
  if (hash.get(obj)) return hash.get(obj);
  let cloneObj = new obj.constructor();
  // 找到的是所属类原型上的constructor,而原型上的 constructor指向的是当前类本身
  hash.set(obj, cloneObj);
  for (let key in obj) {
    if (obj.hasOwnProperty(key)) {
      // 实现一个递归拷贝
      cloneObj[key] = deepClone(obj[key], hash);
    }
  }
  return cloneObj;
}