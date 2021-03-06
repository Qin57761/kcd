<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

    <html>  
    <head>  
        <title>当滚动条滑到底部时自动加载内容</title>  
        <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>  
        <style type="text/css">  
            body{  
                background-color: #808080;  
            }  
            #main{  
                margin:0 auto;  
                width: 960px;  
            }  
            #content{  
                position: absolute;  
                width: 960px;  
            }  
            #img{  
                margin: 0;  
                padding: 0;  
            }  
            #img li{  
                list-style-type: none;  
                background-color: salmon;  
                margin: 0;  
                margin-top:10px;  
                border-bottom: solid 1px hotpink;  
                text-align: center;  
            }  
        </style>  
    </head>  
    <body>  
    <div id="main">  
        <div id="content">  
            <ul id="img">  
                <li>1</li>  
                <li>2</li>  
                <li>3</li>  
                <li>4</li>  
                <li>5</li>  
                <li>6</li>  
                <li>7</li>  
                <li>8</li>  
                <li>9</li>  
                <li>10</li>  
                <li>11</li>  
                <li>12</li>  
                <li>13</li>  
                <li>14</li>  
                <li>15</li>  
                <li>16</li>  
                <li>17</li>  
                <li>18</li>  
                <li>19</li>  
                <li>20</li>  
                <li>21</li>  
                <li>22</li>  
                <li>23</li>  
                <li>24</li>  
                <li>25</li>  
                <li>26</li>  
                <li>27</li>  
                <li>28</li>  
                <li>29</li>  
                <li>30</li>  
            </ul>  
        </div>  
    </div>  
    <script type="text/javascript" src="cskjs_css/jquery-1.11.0.min.js"></script>  
    <script type="text/javascript">  
        //获取列表中的原有内容  
        var content=document.getElementById("img").innerHTML;  
        //每被调用一次，就将网页原有内容添加一份，这个大家可以写自己要加载的内容或指令  
        function addLi(){  
            document.getElementById("img").innerHTML+=content;  
        }  
        /*  
         * 监听滚动条，本来不想用jQuery但是发现js里面监听滚动条的事件不好添加，这边就引用了Jquery的$(obj).scroll();这个方法了  
         */  
        $(window).scroll(function(){  
            //下面这句主要是获取网页的总高度，主要是考虑兼容性所以把Ie支持的documentElement也写了，这个方法至少支持IE8  
            var htmlHeight=document.body.scrollHeight||document.documentElement.scrollHeight;  
            //clientHeight是网页在浏览器中的可视高度，  
            var clientHeight=document.body.clientHeight||document.documentElement.clientHeight;  
            //scrollTop是浏览器滚动条的top位置，  
            var scrollTop=document.body.scrollTop||document.documentElement.scrollTop;  
            //通过判断滚动条的top位置与可视网页之和与整个网页的高度是否相等来决定是否加载内容；  
            if(scrollTop+clientHeight==htmlHeight){  
                 addLi();  
            }  
        })  
    </script>  
    </body>  
    </html>  