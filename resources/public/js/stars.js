$(document).ready(function() {
  if(window.location.pathname.startsWith('/rawdata')) {
    setupGrid();
  } else if (window.location.pathname == '/dashboard') {
    drawVisualization();
  }
});

// isotope stuff
var setupGrid = function() {
  // set up the active tab 
  $('#stars.tab-pane').addClass('active');

  // grab our containers
  var $container = [$('#stars.isotope-data'), 
                    $('#exoplanets.isotope-data'), 
                    $('#galaxies.isotope-data'),
                    $('#clusters.isotope-data')];

  // our buttons for sorting
  var $sortButtons = [$('#stars-sorts'),
                      $('#exoplanets-sorts'),
                      $('#galaxies-sorts'),
                      $('#clusters-sorts')];
  var sortData = [{
                    name: '.name',
                    distly: '.distly parseInt',
                    lum: '.lum parseInt',
                    colorb_v: '.colorb_v parseInt',
                    speed: '.speed parseInt',
                    absmag: '.absmag parseInt',
                    appmag: '.appmag parseInt'
                  },
                  {
                    name: '.name',
                    distance: '.distance parseInt',
                    numplanets: '.numplanets parseInt',
                    texture: '.texture parseInt'
                  },
                  {
                    name: '.name',
                    distly: '.distly parseInt'
                  },
                  {
                    name: '.name',
                    distly: '.distly parseInt',
                    diam: '.diam parseInt',
                    logage: '.logage parseInt',
                    metal: '.metal parseInt'
                  }];

  // initialize isotope on each container
  jQuery.each($container, function (i, elem) {
    elem.isotope({
      itemSelector: '.isotope-data-item',
      layoutMode: 'fitRows',
      getSortData: sortData[i]
    });

    // set up our sort buttons
    $sortButtons[i].on('click', 'button', function() {
      var sortByValue = $(this).attr('data-sort-by');
      elem.isotope({ sortBy: sortByValue });
    });
  });

  // change primary button coloring
  $('.button-group').each( function( i, buttonGroup ) {
    var $buttonGroup = $( buttonGroup );
    $buttonGroup.on( 'click', 'button', function() {
      $buttonGroup.find('.btn-primary').removeClass('btn-primary');
      $( this ).addClass('btn-primary');
    });
  });
}

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