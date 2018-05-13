jQuery.fn.resizeimg = function(width, height) { 
	return this.each(function() {
		var _this = $(this);

		var img = new Image();
		img.onload = function(){
			if (this.width>0 && this.height>0) {
				if (this.width/this.height >= width/height) {
					if (this.width > width) {
						_this.width(width);
						_this.height((this.height * width) / this.width);
					}
					else {
						_this.width(this.width);
						_this.height(this.height);
					}
				}
				else {
					if (this.height > height) {
						_this.height(height);
						_this.width((this.width * height) / this.height);
					}
					else {
						_this.width(this.width);
						_this.height(this.height);
					}
				}
			}
		};
		img.src = $(this).attr("src");
	});
}