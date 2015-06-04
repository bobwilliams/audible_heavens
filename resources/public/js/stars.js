window.addEventListener('load', init, false);

$(document).ready(function() {
  if(window.location.pathname == '/allstars') {
    setupGrid();
  } else if (window.location.pathname == '/dashboard') {
    drawVisualization();
  }
});

// isotope stuff

var setupGrid = function() {
  var $container = $('.isotope-stars').isotope({
    itemSelector: '.isotope-star-item',
    layoutMode: 'fitRows',
    getSortData: {
      name: '.name',
      distly: '.distly parseInt',
      lum: '.lum parseInt',
      colorb_v: '.colorb_v parseInt',
      speed: '.speed parseInt',
      absmag: '.absmag parseInt',
      appmag: '.appmag parseInt'
    }
  });

  // bind sort button click
  $('#sorts').on( 'click', 'button', function() {
    var sortByValue = $(this).attr('data-sort-by');
    $container.isotope({ sortBy: sortByValue });
  });

  // change is-checked class on buttons
  $('.button-group').each( function( i, buttonGroup ) {
    var $buttonGroup = $( buttonGroup );
    $buttonGroup.on( 'click', 'button', function() {
      $buttonGroup.find('.btn-primary').removeClass('btn-primary');
      $( this ).addClass('btn-primary');
    });
  });
}

// audio stuff
var context;

function init() {
  try {
    window.AudioContext = window.AudioContext || window.webkitAudioContext;
    context = new AudioContext();
  }
  catch(e) {
    console.log('Web Audio API is not supported in this browser');
  }
}

$(document).on('click', 'i', function() {
  var sound = $(this).closest('div').data('sound');
  var oscillator = context.createOscillator();
  oscillator.type = 'sine';    
  oscillator.connect(context.destination);
  oscillator.frequency.value = sound * 10; // value in hertz - need to normalize this over the max - min range into a human hearable sound
  oscillator.start();
  setTimeout(function(){ oscillator.stop(); }, 1000);
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