$(document).ready(function() {
  if (window.location.pathname == '/navigate') {
    drawVisualization();
  }
});

$(document).on('click', 'button', function() {
  var sound = $(this).closest('td').data('sound');
  var osc = T("sin", {freq:sound*100, mul:0.5});

  T("timeout", {timeout:1000}).on("ended", function() {
    this.stop();
  }).set({buddies:osc}).start();
});

var graph = null;
var labels = {};

var getKey = function(star){
  return star.x.toString() + star.y.toString() + star.z.toString();
}

// Called when the Visualization API is loaded.
function drawVisualization() {
  // Create and populate a data table.
  var data = new vis.DataSet();

  $.getJSON('/rawdata', function(stars) {  
    for (i in stars) {
      var x = stars[i].x,
          y = stars[i].y,
          z = stars[i].z;
      labels[getKey(stars[i])] = stars[i].label;
      data.add({
            x: x,
            y: y,
            z: z,
            style: stars[i].lum
       });
    }

    // specify options
    var options = {
      width:  '100%',
      height: '100%',
      style: 'dot-color',
      showPerspective: true,
      showGrid: true,
      showShadow: false,
      keepAspectRatio: true,
      verticalRatio: 0.5,
      legendLabel: "Luminosity",
      tooltip: function(star) {return labels[getKey(star)];}
    };

    // create a graph3d
    var container = document.getElementById('mygraph');
    graph3d = new vis.Graph3d(container, data, options);
  });
}