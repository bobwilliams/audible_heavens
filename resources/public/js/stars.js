$(document).ready(function() {
  setupGrid();
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
