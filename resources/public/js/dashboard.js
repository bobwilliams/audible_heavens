$(document).ready(function() {
  drawVisualization();
});

// graphing stuff
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
  var data = [new vis.DataSet(), new vis.DataSet(), new vis.DataSet(), new vis.DataSet()];

  $.getJSON('/rawstars', function(stars) {  
    for (i in stars) {
      var x = stars[i].x,
          y = stars[i].y,
          z = stars[i].z;
      starMemos[getKey(stars[i])] = stars[i];
      data[0].add({x: x, y: y, z: z, style: stars[i].lum});
      data[1].add({x: x, y: y, z: z, style: stars[i].colorb_v});
      data[2].add({x: x, y: y, z: z, style: stars[i].absmag});
      data[3].add({x: x, y: y, z: z, style: stars[i].appmag});
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
      // legendLabel: "Luminosity",
      tooltip: function(star) {return generateTooltip(star);}
    };

    // create a graph3d
    var container = [document.getElementById('lumgraph'), 
                     document.getElementById('colorgraph'),
                     document.getElementById('absmapgraph'),
                     document.getElementById('appmaggraph')];
    graph3d = new vis.Graph3d(container[0], data[0], options);
    graph3d = new vis.Graph3d(container[1], data[1], options);
    graph3d = new vis.Graph3d(container[2], data[2], options);
    graph3d = new vis.Graph3d(container[3], data[3], options);    
  });
}