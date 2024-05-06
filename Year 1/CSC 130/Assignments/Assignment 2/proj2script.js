const APIDev = {
    "APIAdress": "https://api.spotify.com/v1",
    "WebAPIDevelopers" : "https://developer.spotify.com/documentation/web-api/reference/#/",
    "ClientToken": "649ea79ca5564d31bfbae8f0b1fc1f12",
    "DevApp": "https://developer.spotify.com/dashboard/applications/649ea79ca5564d31bfbae8f0b1fc1f12"
}

let hoverElement = null;

//NOT MINE, CREDIT IS GIVEN TO (Unfortunately, I can no longer find the original poster, but this is not mine!)
const generateRandomString = (myLength) => {
    const chars =
      "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz1234567890";
    const randomArray = Array.from(
      { length: myLength },
      (v, k) => chars[Math.floor(Math.random() * chars.length)]
    );
  
const randomString = randomArray.join("");
  return randomString;
};

const mainUrl = 'http://webhome.csc.uvic.ca/~lukekuligowicz/project2.html';

const userInterface = {
  accessToken: "filler",
  tokenType: "filler",
  expire: "filler",
  state: "filler"
}
async function onPageLoad() {
  if (document.location.href.indexOf( "#" ) > 0) {
    await $(".sign-in-class").css("display", "none");
    await handleKey();
  }
  else {
    await $(".log-in-class").css("display", "none");
  }

  const allHoverElements = document.getElementsByClassName("hover-effect");
  for (let i = 0; i < allHoverElements.length; i ++) {
    let curElement = allHoverElements[i];
    curElement.addEventListener('mouseenter', function() {findHoveredElement(this)});
  }
}
function signIn() {
  if (document.location.href.indexOf( "#" ) < 0) {
    authorize();
  }
  else {
    handleRedirect();
  }
}
function logOut() {
  document.location = mainUrl;
}

function authorize() {
    var client_id = '649ea79ca5564d31bfbae8f0b1fc1f12';
    var redirect_uri = mainUrl;

    var state = generateRandomString(16);
    var stateKey = generateRandomString(16);
 
    localStorage.setItem(stateKey, state);
    var scope = 'user-read-private user-read-email user-top-read';

    var url = 'https://accounts.spotify.com/authorize';
    url += '?response_type=token';
    url += '&client_id=' + encodeURIComponent(client_id);
    url += '&scope=' + encodeURIComponent(scope);
    url += '&redirect_uri=' + encodeURIComponent(redirect_uri);
    url += '&show_dialog=' + encodeURIComponent('true');
    url += '&state=' + encodeURIComponent(state);

    document.location.href = url;
}

function handleRedirect() {
    console.log("Handling Redirect");
    try {
     handleKey();
    }
    catch (e) {
      const errorMessage = 'Exception: ${e.message}';
      console.log(errorMessage);
    }
}

function handleKey() {
    let url = document.location.href;
    url = url.split("#");
    url = url[1].split("&");
    userInterface.accessToken = url[0];
    userInterface.tokenType = url[1];
    userInterface.expire = url[2];
    userInterface.state = url[3];
    console.log(userInterface);
}

function handleTransitionIn(selector) {
  $(".pod").each(function() {
    $(this).addClass('shrink')
  })
  $('.' + selector + '-ui').addClass('trans-in')
}

function handleTransitionOut(selector) {
  $(".pod").each(function() {
    $(this).removeClass('shrink')
  })
  $('.' + selector + '-ui').removeClass('trans-in')
  $('.' + selector + '-ui').removeClass('display');
  hoverElement = null;
  console.log(hoverElement);
}

function createTrackElement(data, id) {
  var divElement = document.createElement('div');
  var img = document.createElement('img');
  var name = document.createElement('p');
  var artist = document.createElement('p');

  img.setAttribute('src', data.album.images['0'].url);
  img.setAttribute('alt','image failed to load');
  img.setAttribute('class', 'track-image');

  name.innerHTML = data.name;
  artist.innerHTML = data.artists['0'].name;

  divElement.setAttribute('class', 'track-display');
  divElement.appendChild(img);
  divElement.appendChild(name);
  divElement.appendChild(artist);
  document.getElementById(id).appendChild(divElement);
}

function createArtistElement(data, id) {
  var divElement = document.createElement('div');
  var img = document.createElement('img');
  var artist = document.createElement('p');

  console.log(data.images);

  img.setAttribute('src', data.images['0'].url);
  img.setAttribute('alt','image failed to load');
  img.setAttribute('class', 'track-image');

  artist.innerHTML = data.name;

  divElement.setAttribute('class', 'track-display');
  divElement.appendChild(img);
  divElement.appendChild(artist);
  console.log(id);
  document.getElementById(id).appendChild(divElement);
}

async function transitionProfile(selector) {
  console.log("transitioning to profile ui");
  await handleTransitionIn(selector);
  if (!$('.profile-container').hasClass('contains-content')) {
    console.log("creating profile ui");
    let topArtists = await returnData('/me/top/artists/', '?time_range=short_term&limit=10&offset=0')
    topArtists = await JSON.parse(topArtists);

    let keys = Object.keys(topArtists.items);

    let currentArtists = '';

    for (let i = 0; i < keys.length; i++) {
      createArtistElement(topArtists.items[keys[i]], "top-artists-prof");
      let recommendedArtist = await returnData('/artists/', topArtists.items[keys[i]].id + '/related-artists/');
      recommendedArtist = await JSON.parse(recommendedArtist);
      
      var count = 0;
      while (currentArtists.includes(recommendedArtist.artists[keys[count]].name)) {
        count ++;
      }
      createArtistElement(recommendedArtist.artists[keys[count]], 'rec-artists-prof');
      currentArtists += recommendedArtist.artists[keys[count]].name;
    }


    let topTracks = await returnData('/me/top/tracks', '?time_range=short_term&limit=10&offset=0');
    topTracks = await JSON.parse(topTracks);

    keys = Object.keys(topTracks.items);
    let currentTracks = ''

    for (let i = 0; i < keys.length; i++) {
      createTrackElement(topTracks.items[keys[i]], "top-tracks-prof");
      let recommendedTracks = await returnData('/recommendations', '?limit=10&seed_tracks=' + topTracks.items[keys[i]].id);
      recommendedTracks = await JSON.parse(recommendedTracks);
      
      var count = 0;
      while (currentTracks.includes(recommendedTracks.tracks[keys[count]].name)) {
        count ++;
      }
      createTrackElement(recommendedTracks.tracks[keys[count]], 'rec-tracks-prof');
        currentArtists += recommendedTracks.tracks[keys[count]].name;
      }
      $('.profile-container').addClass('contains-content');
      document.getElementById('json-storage').innerHTML = '';
  }
  $('.profile-container').addClass('display');
  hoverElement = document.getElementById('first-find-prof');
  findHoveredElement(hoverElement);
}

async function transitionInputRec(selector) {
  console.log("transitioning to input rec ui");
  await handleTransitionIn(selector);
}


async function returnData(func, id) {
  let apiUrl = APIDev.APIAdress + encodeURI(func) + encodeURI(id);
  let authority = userInterface.tokenType.split('=')[1] + ' ' + userInterface.accessToken.split('=')[1];
  await $.ajax ({
    url: apiUrl,
    type: 'GET',
    headers: {
      'Authorization': authority
    },
    success: function(data) {
      console.log(data);
      document.getElementById('json-storage').innerHTML = JSON.stringify(data);
    }
  });
  if (document.getElementById('json-storage').innerHTML == '{"artists":[]}') {
    document.getElementById('json-storage').innerHTML = '';
    throw "Broke, Continue";
  }
  return document.getElementById('json-storage').textContent;
}

function findHoveredElement(element) {
  hoverElement = element;
  correctHoverElement();
}

function correctHoverElement() {
  $(".cur-display-container").each(function() {
    $(this).removeClass('cur-display-container');
  });

  const toFindId = hoverElement.getAttribute("name");
  const toChangeElement = document.getElementById(toFindId);
  $(toChangeElement).addClass('cur-display-container');
}

async function handleSearchQuery(selector) {
  console.log(hoverElement);
  let track = $('#' + selector + '-track').val();
  let artist = $('#' + selector + '-artist').val();
  let searchURL = ((track == '')? '' : track + ',') + artist + "&type=track,artist";
  
  if (track != '' || artist != '') {
    document.getElementById("input-rec-artists").innerHTML = '';
    document.getElementById("input-rec-tracks").innerHTML = '';
    let returnSearchData = await returnData('/search?q=', searchURL);
    returnSearchData = await JSON.parse(returnSearchData);

    console.log(returnSearchData);
    const returnArtists = returnSearchData.artists;
    const returnTracks = returnSearchData.tracks;

    const keys = Object.keys(returnSearchData.tracks.items);

    for (let i = 0; i < returnArtists.items.length; i++) {
      try {
        console.log(i);
        console.log(returnTracks);
        let recommendedTracks = await returnData('/recommendations', '?limit=10&seed_tracks=' + returnTracks.items[keys[i]].id);
        recommendedTracks = await JSON.parse(recommendedTracks);
        console.log(returnArtists);
        let recommendedArtist = await returnData('/artists/', returnArtists.items[keys[i]].id + '/related-artists/');
        recommendedArtist = JSON.parse(recommendedArtist);

        console.log(recommendedTracks);
        console.log(recommendedArtist)
        createTrackElement(recommendedTracks.tracks['0'], 'input-rec-tracks');
        if (recommendedArtist.artists.length > 0 && recommendedArtist.artists['0'].images.length > 0) {
        createArtistElement(recommendedArtist.artists['0'], 'input-rec-artists');
          }
        }
      catch(err) {
        continue;
      }
      }
    }
  else {
    alert("Please input some text in the input slots!")
  }
  $('.input-rec-container').addClass('display');
  hoverElement = document.getElementById('first-find-rec');
  findHoveredElement(hoverElement);
}

async function transitionInputInfo(selector) {
  console.log("transitioning to input info ui");
  await handleTransitionIn(selector);
}
async function handleInfoSearch(selector) {
  let track = $('#' + selector + '-track').val();
  let artist = $('#' + selector + '-artist').val();
  let searchURL = ((track == '')? '' : track + ',') + artist + "&type=track";
  
  if (track != '' || artist != '') {
    let returnSearchData = await returnData('/search?q=', searchURL);
    returnSearchData = await JSON.parse(returnSearchData);

    console.log(returnSearchData.tracks.items['0']);
    const curTrack = returnSearchData.tracks.items['0'];
   
    const albumName = curTrack.album.name;
    const artists = curTrack.artists['0'].name;
    const durationMS = curTrack.duration_ms;
    const trackID = curTrack.id;
    const name = curTrack.name;
    const popularity = curTrack.popularity;

    document.getElementById('album-name').innerHTML = 'Album Name: ' + albumName;
    document.getElementById('artists').innerHTML = 'Artist: ' + artists;
    document.getElementById('input-info-art').innerHTML = artists;    
    document.getElementById('duration-ms').innerHTML = 'Duration ms: ' + durationMS;
    document.getElementById('track-id').innerHTML = 'Track ID: ' + trackID;
    document.getElementById('name').innerHTML = 'Track Name: ' + name;
    document.getElementById('input-info-name').innerHTML = name;
    document.getElementById('popularity').innerHTML = 'Popularity: ' + popularity;
    document.getElementById('input-info-img').setAttribute('src', curTrack.album.images['0'].url); 

  }
}