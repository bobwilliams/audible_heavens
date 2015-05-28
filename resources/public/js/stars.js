$(document).on('click', 'button', function() {
  var sound = $(this).closest('tr').data('lum');
  var osc = T("sin", {freq:sound*1000, mul:0.5});

  T("timeout", {timeout:1000}).on("ended", function() {
    this.stop();
  }).set({buddies:osc}).start();
});
