//Map是一组键值对的结构，具有极快的查找速度。
var names = ['Michael', 'Bob', 'Tracy'];
var scores = [95, 75, 85];

/*给定一个名字，要查找对应的成绩，就先要在names中找到对应的位置，再从scores取出对应的成绩，Array越长，耗时越长。
如果用Map实现，只需要一个“名字”-“成绩”的对照表，直接根据名字查找成绩，无论这个表有多大，查找速度都不会变慢。用JavaScript写一个Map如下：*/

var m = new Map([['Michael', 95], ['Bob', 75], ['Tracy', 85]]);
m.get('Michael'); // 95

var m = new Map(); // 空Map
m.set('Adam', 67); // 添加新的key-value
m.set('Bob', 59);
m.has('Adam'); // 是否存在key 'Adam': true
m.get('Adam'); // 67
m.delete('Adam'); // 删除key 'Adam'
m.get('Adam'); // undefined

//Set Set和Map类似，也是一组key的集合，但不存储value。由于key不能重复，所以，在Set中，没有重复的key。
var s1 = new Set(); // 空Set
var s2 = new Set([1, 2, 3]); // 含1, 2, 3

/*
  函数
 */
/**
 * function指出这是一个函数定义；
 * abs是函数的名称；
 * (x)括号内列出函数的参数，多个参数以,分隔；
 * { ... }之间的代码是函数体，可以包含若干语句，甚至可以没有任何语句。
 * @param x
 * @returns {*}
 */
function abs(x) {
  if (x >= 0) {
    return x;
  } else {
    return -x;
  }
}

/*
  由于JavaScript的函数也是一个对象，上述定义的abs()函数实际上是一个函数对象，而函数名abs可以视为指向该函数的变量。
 */
var abs = function (x) {
  if (x >= 0) {
    return x;
  } else {
    return -x;
  }
};
/*
  在这种方式下，function (x) { ... }是一个匿名函数，它没有函数名。但是，这个匿名函数赋值给了变量abs，所以，通过变量abs就可以调用该函数。
 */

abs(10); // 返回10
abs(-9); // 返回9

/*
  函数的参数x将收到undefined，计算结果为NaN
  要避免收到undefined，可以对参数进行检查：
 */
function abs(x) {
  if (typeof x !== 'number') {
    throw 'Not a number';
  }
  if (x >= 0) {
    return x;
  } else {
    return -x;
  }
}

//arguments
/*
  只在函数内部起作用，并且永远指向当前函数的调用者传入的所有参数。arguments类似Array但它不是一个Array：
 */
function foo(x) {
  console.log('x = ' + x); // 10
  for (var i = 0; i < arguments.length; i++) {
    console.log('arg ' + i + ' = ' + arguments[i]); // 10, 20, 30
  }
}

foo(10, 20, 30);

/*
  利用arguments，你可以获得调用者传入的所有参数。也就是说，即使函数不定义任何参数，还是可以拿到参数的值
 */
function abs() {
  if (arguments.length === 0) {
    return 0;
  }
  var x = arguments[0];
  return x >= 0 ? x : -x;
}

abs(); // 0
abs(10); // 10
abs(-9); // 9

function foo(a, b, ...rest) {
  console.log('a = ' + a);
  console.log('b = ' + b);
  console.log(rest);
}

foo(1, 2, 3, 4, 5);
// 结果:
// a = 1
// b = 2
// Array [ 3, 4, 5 ]
// 如果传入的参数连正常定义的参数都没填满，也不要紧，rest参数会接收一个空数组（注意不是undefined）。

/*
  如果一个变量在函数体内部申明，则该变量的作用域为整个函数体，在函数体外不可引用该变量：
 */
function foo() {
  var x = 1;
  x = x + 1;
}

x = x + 2; // ReferenceError! 无法在函数体外引用变量x

function foo() {
  var x = 1;

  function bar() {
    var y = x + 1; // bar可以访问foo的变量x!
  }

  var z = y + 1; // ReferenceError! foo不可以访问bar的变量y!
}

/*
  不在任何函数内定义的变量就具有全局作用域
  var 不具有块级作用域
  ES6引入let，const
  let申明块级变量
  const申明常量
  const app = {}; app.name = 'zjp'; 因为const 指向对象在内存中的地址，它不会变。往对象中添加值不会改变对象在内存中的地址。
 */

/*
  解构赋值
 */
// ES5
var array = ['hello', 'JavaScript', 'ES6'];
var x = array[0];
var y = array[1];
var z = array[2];

// ES6
var [x, y, z] = ['hello', 'JavaScript', 'ES6'];
let [x, [y, z]] = ['hello', ['JavaScript', 'ES6']];
let [, , z] = ['hello', 'JavaScript', 'ES6']; // 忽略前两个元素，只对z赋值第三个元素
z; // 'ES6'

var person = {
  name: '小明',
  age: 20,
  gender: 'male',
  passport: 'G-12345678',
  school: 'No.4 middle school'
};
var {name, age, passport} = person;

var person = {
  name: '小明',
  age: 20,
  gender: 'male',
  passport: 'G-12345678',
  school: 'No.4 middle school',
  address: {
    city: 'Beijing',
    street: 'No.1 Road',
    zip_code: '100001'
  }
};
var {name, address: {city, zip}} = person;
name; // '小明'
city; // 'Beijing'
zip; // undefined, 因为属性名是zipcode而不是zip
// 注意: address不是变量，而是为了让city和zip获得嵌套的address对象的属性:
address; // Uncaught ReferenceError: address is not defined

// 解构赋值还可以使用默认值，这样就避免了不存在的属性返回undefined的问题：
var person = {
  name: '小明',
  age: 20,
  gender: 'male',
  passport: 'G-12345678'
};

// 如果person对象没有single属性，默认赋值为true:
var {name, single = true} = person;
name; // '小明'
single; // true

/*
  apply
  apply方法，它接收两个参数，第一个参数就是需要绑定的this变量，第二个参数是Array，表示函数本身的参数。
 */
function getAge() {
  var y = new Date().getFullYear();
  return y - this.birth;
}

var xiaoming = {
  name: '小明',
  birth: 1990,
  age: getAge
};

xiaoming.age(); // 25
getAge.apply(xiaoming, []); // 25, this指向xiaoming, 参数为空

/*
  另一个与apply()类似的方法是call()，唯一区别是：
    apply()把参数打包成Array再传入；
    call()把参数按顺序传入。
 */
Math.max.apply(null, [3, 5, 4]); // 5
Math.max.call(null, 3, 5, 4); // 5


var arr = ['apple', 'strawberry', 'banana', 'pear', 'apple', 'orange', 'orange', 'strawberry'];

var r = arr.filter(function (element, index, self) {
  return self.indexOf(element) === index;
});
// 去除重复元素依靠的是indexOf总是返回第一个元素的位置，后续的重复元素位置与indexOf返回的位置不相等，因此被filter滤掉了。


/*
  Array的sort()方法默认把所有元素先转换为String再排序
 */
[10, 20, 1, 2].sort(); // [1, 10, 2, 20]
//结果'10'排在了'2'的前面，因为字符'1'比字符'2'的ASCII码小。

// sort()方法也是一个高阶函数，它还可以接收一个比较函数来实现自定义的排序。
var arr = [10, 20, 1, 2];
arr.sort(function (x, y) {
  if (x < y) {
    return -1;
  }
  if (x > y) {
    return 1;
  }
  return 0;
});

// 倒序排序
var arr = [10, 20, 1, 2];
arr.sort(function (x, y) {
  if (x < y) {
    return 1;
  }
  if (x > y) {
    return -1;
  }
  return 0;
}); // [20, 10, 2, 1]

// 忽略大小写来比较两个字符串，实际上就是先把字符串都变成大写（或者都变成小写），再比较。

/*
  函数作为返回值
 */

function lazy_sum(arr) {
  var sum = function () {
    return arr.reduce(function (x, y) {
      return x + y;
    });
  };
  return sum;
}

//调用lazy_sum()时，返回的并不是求和结果，而是求和函数：
var f = lazy_sum([1, 2, 3, 4, 5]); // function sum()
// 调用函数f时，才真正计算求和的结果：
f(); // 15

/*
  在函数lazy_sum中又定义了函数sum，并且，内部函数sum可以引用外部函数lazy_sum的参数和局部变量，当lazy_sum返回函数sum时，相关参数和变量都保存在返回的函数中，这种称为“闭包（Closure）”
 */

/*
  闭包 ： 返回函数不要引用任何循环变量，或者后续会发生变化的变量
 */

function count() {
  var arr = [];
  for (var i = 1; i <= 3; i++) {
    arr.push((function (n) {
      return function () {
        return n * n;
      }
    })(i));
  }
  return arr;
}

var results = count();
var f1 = results[0];
var f2 = results[1];
var f3 = results[2];

f1(); // 1
f2(); // 4
f3(); // 9

// 创建一个匿名函数并立刻执行”的语法：
(function (x) {
  return x * x;
})(3); // 9

/*
  箭头函数
 */
// 两个参数:
(x, y) => x * x + y * y;

// 无参数:
() => 3.14;

// 可变参数:
(x, y, ...rest) => {
  var i, sum = x + y;
  for (i = 0; i < rest.length; i++) {
    sum += rest[i];
  }
  return sum;
};

//返回一个对象是单表达式
x => ({foo: x})

// 箭头函数完全修复了this的指向，this总是指向词法作用域，也就是外层调用者obj
var obj = {
  birth: 1990,
  getAge: function () {
    var b = this.birth; // 1990
    var fn = () => new Date().getFullYear() - this.birth; // this指向obj对象
    return fn();
  }
};
obj.getAge(); // 31

// 用call()或者apply()调用箭头函数时，无法对this进行绑定，即传入的第一个参数被忽略：

var obj = {
  birth: 1990,
  getAge: function (year) {
    var b = this.birth; // 1990
    var fn = (y) => y - this.birth; // this.birth仍是1990
    return fn.call({birth: 2000}, year);
  }
};
obj.getAge(2015);

/*
  generator（生成器）是ES6标准引入的新的数据类型。一个generator看上去像一个函数，但可以返回多次。
 */

function* foo(x) {
  yield x + 1;
  yield x + 2;
  return x + 3;
}

// 函数只能返回一次，所以必须返回一个Array

function fib(max) {
  var
    t,
    a = 0,
    b = 1,
    arr = [0, 1];
  while (arr.length < max) {
    [a, b] = [b, a + b];
    arr.push(b);
  }
  return arr;
}

// 换成generator，就可以一次返回一个数，不断返回多次
function* fib(max) {
  var
    t,
    a = 0,
    b = 1,
    n = 0;
  while (n < max) {
    yield a;
    [a, b] = [b, a + b];
    n++;
  }
  return;
}

var f = fib(5);
// 调用generator对象有两个方法，一是不断地调用generator对象的next()方法：
f.next(); // {value: 0, done: false}
f.next(); // {value: 1, done: false}
f.next(); // {value: 1, done: false}
f.next(); // {value: 2, done: false}
f.next(); // {value: 3, done: false}
f.next(); // {value: undefined, done: true}

// next()方法会执行generator的代码，然后，每次遇到yield x;就返回一个对象{value: x, done: true/false}，然后“暂停”。
// 返回的value就是yield的返回值，done表示这个generator是否已经执行结束了。
// 如果done为true，则value就是return的返回值。

for (var x of fib(10)) {
  console.log(x); // 依次输出0, 1, 1, 2, 3, ...
}

// generator还有另一个巨大的好处，就是把异步回调代码变成“同步”代码

ajax('http://url-1', data1, function (err, result) {
  if (err) {
    return handle(err);
  }
  ajax('http://url-2', data2, function (err, result) {
    if (err) {
      return handle(err);
    }
    ajax('http://url-3', data3, function (err, result) {
      if (err) {
        return handle(err);
      }
      return success(result);
    });
  });
});

/*
  try {
  r1 = yield ajax('http://url-1', data1);
  r2 = yield ajax('http://url-2', data2);
  r3 = yield ajax('http://url-3', data3);
  success(r3);
}
catch (err) {
  handle(err);
}
 */

/**
 * 不要使用new Number()、new Boolean()、new String()创建包装对象；
 * 用parseInt()或parseFloat()来转换任意类型到number；
 * 用String()来转换任意类型到string，或者直接调用某个对象的toString()方法；
 * 通常不必把任意类型转换为boolean再判断，因为可以直接写if (myVar) {...}；
 * typeof操作符可以判断出number、boolean、string、function和undefined；
 * 判断Array要使用Array.isArray(arr)；
 * 判断null请使用myVar === null；
 * 判断某个全局变量是否存在用typeof window.myVar === 'undefined'；
 * 函数内部判断某个变量是否存在用typeof myVar === 'undefined'。
 * null和undefined无toString()方法
 * number对象调用toString()报SyntaxError 123..toString(); // '123', 注意是两个点！
 */

/*
  JavaScript对每个创建的对象都会设置一个原型，指向它的原型对象。
  当我们用obj.xxx访问一个对象的属性时，JavaScript引擎先在当前对象上查找该属性，
    如果没有找到，就到其原型对象上找，
    如果还没有找到，就一直上溯到Object.prototype对象，
    最后，如果还没有找到，就只能返回undefined。
 */

/*
  创建一个Array对象
  其原型链 arr ----> Array.prototype ----> Object.prototype ----> null
 */

/*
  创建一个函数时
  其原型链 foo ----> Function.prototype ----> Object.prototype ----> null
  由于Function.prototype定义了apply()等方法，因此，所有函数都可以调用apply()方法。
 */

function Student(name) {
  this.name = name;
  this.hello = function () {
    alert('Hello, ' + this.name + '!');
  }
}

// 用关键字new来调用这个函数，并返回一个对象：
var xiaoming = new Student('小明');
xiaoming.name; // '小明'
xiaoming.hello(); // Hello, 小明!

/*
  如果不写new，这就是一个普通函数，它返回undefined。
  使用new，它就变成了一个构造函数，它绑定的this指向新创建的对象，并默认返回this
  xiaoming ----> Student.prototype ----> Object.prototype ----> null
 */

/*
  用new Student()创建的对象还从原型上获得了一个constructor属性，它指向函数Student本身
 */

xiaoming.constructor === Student.prototype.constructor; // true
Student.prototype.constructor === Student; // true

Object.getPrototypeOf(xiaoming) === Student.prototype; // true

xiaoming instanceof Student; // true

// ES6
class Student{
  constructor(name) {
    this.name = name;
  }

  hello() {
    console.log('hello {}', this.name);
  }
}

// class的定义包含了构造函数constructor和定义在原型对象上的函数hello()

var xiaoming = new Student('小明');
xiaoming.hello();

class PrimaryStudent extends Student{
  constructor(name, grade) {
    super(name); // 记得用super调用父类的构造方法!
    this.grade = grade;
  }

  myGrade() {
    alert('I am at grade ' + this.grade);
  }
}

// http://www.example.com:8080/path/index.html?a=1&b=2#TOP

location.protocol; // 'http'
location.host; // 'www.example.com'
location.port; // '8080'
location.pathname; // '/path/index.html'
location.search; // '?a=1&b=2'
location.hash; // 'TOP'



