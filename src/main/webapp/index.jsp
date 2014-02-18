<html>
	<body>
		<h2>
			Hello World!123
		</h2>
		<script src="http://code.jquery.com/jquery.min.js"></script>
		<script type="text/javascript">
	$.ajax( {
		url : './u/4.do',
		type:"post",
		dataType:'json',
		success : function(d) {
			console.log(d);
		}
	});
</script>
	</body>
</html>
