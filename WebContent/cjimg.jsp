<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Basic Handler | Jcrop Demo</title>
  <meta http-equiv="Content-type" content="text/html;charset=UTF-8" />
  <script src="../kcd/cjjs/jquery.min.js"></script>
  <script src="../kcd/cjjs/jquery.Jcrop.js"></script>
  <link rel="stylesheet" href="../ccss/jquery.Jcrop.css" type="text/css" />
<script type="text/javascript">
  jQuery(function($){
    var jcrop_api;
    $('#target').Jcrop({
      onChange:   showCoords,
      onSelect:   showCoords,
      onRelease:  clearCoords
    },function(){
      jcrop_api = this;
    });

    $('#coords').on('change','input',function(e){
      var x1 = $('#x1').val(),
          x2 = $('#x2').val(),
          y1 = $('#y1').val(),
          y2 = $('#y2').val();
      jcrop_api.setSelect([x1,y1,x2,y2]);
    });

  });

  // Simple event handler, called from onChange and onSelect
  // event handlers, as per the Jcrop invocation above
  function showCoords(c)
  {
    $('#x1').val(c.x);
    $('#y1').val(c.y);
    $('#x2').val(c.x2);
    $('#y2').val(c.y2);
    $('#w').val(c.w);
    $('#h').val(c.h);
  };

  function clearCoords()
  {
    $('#coords input').val('');
  };



</script>
<link rel="stylesheet" href="demo_files/main.css" type="text/css" />
<link rel="stylesheet" href="demo_files/demos.css" type="text/css" />


</head>
<body>

<div class="container">
<div class="row">
<div class="span12">
<div class="jc-demo-box">
<div class="page-header">
<ul class="breadcrumb first">
  <li><a href="../index.html">Jcrop</a> <span class="divider">/</span></li>
  <li><a href="../index.html">Demos</a> <span class="divider">/</span></li>
  <li class="active">Basic Handler</li>
</ul>
<h1>Basic Handler</h1>
</div>

  <!-- This is the image we're attaching Jcrop to -->
  <img src="demo_files/sago.jpg" id="target"  />

  <!-- This is the form that our event handler fills -->
  <form id="coords"
    class="coords"
    onsubmit="return false;"
    action="http://example.com/post.php">

    <div class="inline-labels">
    <label>X1 <input type="text" size="4" id="x1" name="x1" /></label>
    <label>Y1 <input type="text" size="4" id="y1" name="y1" /></label>
    <label>X2 <input type="text" size="4" id="x2" name="x2" /></label>
    <label>Y2 <input type="text" size="4" id="y2" name="y2" /></label>
    <label>W <input type="text" size="4" id="w" name="w" /></label>
    <label>H <input type="text" size="4" id="h" name="h" /></label>
    </div>
  </form>

  <div class="description">
  <p>
    <b>An example with a basic event handler.</b> Here we've tied
    several form values together with a simple event handler invocation.
    The result is that the form values are updated in real-time as
    the selection is changed using Jcrop's <em>onChange</em> handler.
  </p>

  <p>
    That's how easily Jcrop can be integrated into a traditional web form!
  </p>
  </div>


<div class="tapmodo-footer">
  <a href="http://tapmodo.com" class="tapmodo-logo segment">tapmodo.com</a>
  <div class="segment"><b>&copy; 2008-2013 Tapmodo Interactive LLC</b><br />
    Jcrop is free software released under <a href="../MIT-LICENSE.txt">MIT License</a>
  </div>
</div>

<div class="clearfix"></div>

</div>
</div>
</div>
</div>

</body>
</html>

