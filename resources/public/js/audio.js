window.addEventListener('load', init, false);

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
