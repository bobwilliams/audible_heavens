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
var starMemos = {};

var getKey = function(star){
  return star.x.toString() + star.y.toString() + star.z.toString();
}

var generateTooltip = function(star){
  var memoStar = starMemos[getKey(star)];
  var html = "<h5>Name: " + memoStar.label + "</h5>";
  html += "<p>Coords: (" + memoStar.x + ", " + memoStar.y + ", " + memoStar.z + ")";
  html += "<br>Distance: " + memoStar.distly;
  html += "<br>Luminosity: " + memoStar.lum;
  html += "<br>Color: " + memoStar.colorb_v;
  html += "<br>Speed: " + memoStar.speed;
  html += "<br>Abs Mag: " + memoStar.absmag;
  html += "<br>App Mag: " + memoStar.appmag; 
  return html;
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
      starMemos[getKey(stars[i])] = stars[i];
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
      tooltip: function(star) {return generateTooltip(star);}
    };

    // create a graph3d
    var container = document.getElementById('mygraph');
    graph3d = new vis.Graph3d(container, data, options);
  });
}