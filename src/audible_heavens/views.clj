(ns audible_heavens.views
  (:require [hiccup.core :refer :all]
            [audible_heavens.global :as g]))

(def bs-columns ["col-sm-1","col-sm-2","col-sm-3","col-sm-4","col-sm-5","col-sm-6","col-sm-7","col-sm-8","col-sm-9","col-sm-10","col-sm-11","col-sm-12"])

(defn common-head [& extras]
  [:head
    [:title @g/brand-name]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
    [:link {:href "/static/bootstrap/css/bootstrap.min.css" :rel "stylesheet" :media "screen"}]
    [:script {:src "http://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"}]
    [:script {:src "/static/bootstrap/js/bootstrap.min.js"}]
    [:script {:src "/static/js/stars.js"}]
    [:script {:src "/static/js/vis/dist/vis.js"}]
    [:script {:src "//cdnjs.cloudflare.com/ajax/libs/jquery.isotope/2.2.0/isotope.pkgd.min.js"}]
    [:link {:href "/static/bootstrap/css/lavish-theme.css" :rel "stylesheet" :media "screen"}]
    [:link {:href "/static/css/audible_heavens.css" :rel "stylesheet" :media "screen"}]
    extras])

(defn forkme []
  [:a {:href "https://github.com/bobwilliams/audible_heavens"}
    [:img {:style "position: absolute; top: 51; right: 0; border: 0; z-index: 9000"
           :src "https://camo.githubusercontent.com/365986a132ccd6a44c23a9169022c0b5c890c387/68747470733a2f2f73332e616d617a6f6e6177732e636f6d2f6769746875622f726962626f6e732f666f726b6d655f72696768745f7265645f6161303030302e706e67"
           :alt "Fork me on GitHub" 
           :data-canonical-src="https://s3.amazonaws.com/github/ribbons/forkme_right_red_aa0000.png"}]])

(defn nav-bar []
  [:div.navbar.navbar-inverse.navbar-fixed-top {:role "navigation"}
    [:div.container
      [:div.navbar-header
        [:button.btn.navbar-toggle {:data-toggle "collapse" :data-target "#menu-nav-collapse"}
          [:span.sr-only "Toggle Navigation"]
          [:span.icon-bar]
          [:span.icon-bar]
          [:span.icon-bar]]
        [:a#brand.navbar-brand {:href "/"} @g/brand-name]]
      [:div#menu-nav-collapse.collapse.navbar-collapse
        [:ul.nav.navbar-nav.navbar-right
          [:li [:a {:href "/allstars"} "All Stars" ]]
          [:li [:a {:href "/dashboard"} "Dashboard" ]]]]]])

[:a.btn.btn-primary {:href "/db"} "Let's get started..."]

(defn jumbotron [title sub-title]
  [:div.jumbotron
    [:h1 title]
    [:p sub-title]])

(defn page-header [text sub-text]
  [:div.page-header
    [:h1 text [:br] [:small sub-text]]])

(defn common-footer []
  [:div#footer.footer
    [:div.text-muted "Copyright &copy; 2015 Bob Williams. All Rights Reserved"]])

(defn dropdown-item [item]
  [:option {:value item} item])

(defn dropdown-item-default [item]
  [:option {:value item :selected "selected"} item])

(defn dropdown
  ([items id] (dropdown (rest items) (first items) id))
  ([items default id]
    [:select.query-select.form-control {:id id}
      (cons 
        (dropdown-item-default default)
        (map dropdown-item items))]))

(defn breadcrumb [[name url]]
  [:li
    [:a {:href url} name]])

(defn breadcrumbs [crumbs]
  [:ol.breadcrumb
    (map breadcrumb crumbs)])

(defn linked-item [[name url]]
  [:a.list-group-item {:href url} name])

(defn linked-items [litems]
  [:div.list-group
    (map linked-item litems)])

(defn input [label]
  [:input#match-val.query-input.form-control {:type "text" :placeholder label}])

(defn label [label target]
  [:label.col-sm-2.control-label {:for target} label])

(defn group [el & extras]
  [:div.form-group el extras])

(defn ctl 
  ([el] (ctl el 4))
  ([el col] 
    [:div {:class (nth bs-columns (- col 1))} el]))

(defn td-sound [value]
  [:td {:data-sound value} 
    [:button.btn.btn-default.glyphicon.glyphicon-play {:style "width: 100%;"} (str "&nbsp;" value)]])

(defn star-item [value label selector]
  [:div {:data-sound value} 
    [:i.glyphicon.glyphicon-music] 
    "&nbsp;&nbsp;" 
    [:span.text-info (str label ": ")] 
    [selector value]])

(defn star-row [star]
  [:div.isotope-star-item.well
    [:h4.name.text-primary.strong (get star :label)]
    [:small.coords.text-warning (str "(" (get star :x) ", " (get star :y) ", " (get star :z) ")" )]
    [:hr]
    (star-item (get star :distly) "Distance" :span.distance.text-success)
    (star-item (get star :lum) "Luminosity" :span.luminosity.text-success)
    (star-item (get star :colorb_v) "Color" :span.color.text-success)
    (star-item (get star :speed) "Speed" :span.speed.text-success)
    (star-item (get star :absmag) "Abs Mag" :span.absmag.text-success)
    (star-item (get star :appmag) "App Mag" :span.appmag.text-success)])

(defn welcome-view []
  (html
    [:html
      (common-head)
      [:body
        (nav-bar)
        (forkme)
        [:div.container
          (breadcrumbs [["home" "/"]])
          (jumbotron (str "Welcome to " @g/brand-name "!") "An simple web app that allows you to audibly explore the heavens.")
          [:h4 "This application consumes data provide by the " 
            [:a {:href "http://star-api.herokuapp.com/"} "Star API. "]
            "A big shout out to the folks over at " 
            [:a {:href "https://github.com/HacktheUniverse"} "Hack the Universe"] " for exposing this data."]]
        (common-footer)]]))

(defn allstars [stars]
  (html
    [:html
      (common-head)
      [:body
        (nav-bar)
        [:div.container
          (breadcrumbs [["home" "/"] ["all stars" "/allstars"]])
          (page-header "All Stars" (str "showing data for " (count stars) " stars"))
          [:h4 "Sort By"]
          [:div#sorts.button-group
            [:button.btn.btn-default.btn-primary {:data-sort-by "original"} "original order"]
            [:button.btn.btn-default {:data-sort-by "name"} "name"]
            ; [:button.btn.btn-default {:data-sort-by "coords"} "Coords"]
            [:button.btn.btn-default {:data-sort-by "distance"} "distance"]
            [:button.btn.btn-default {:data-sort-by "luminosity"} "luminosity"]
            [:button.btn.btn-default {:data-sort-by "color"} "color"]
            [:button.btn.btn-default {:data-sort-by "speed"} "speed"]
            [:button.btn.btn-default {:data-sort-by "absmag"} "abs mag"]
            [:button.btn.btn-default {:data-sort-by "appmag"} "app mag"]]
          [:br]
          [:div#star-data.isotope-stars
            (map #(star-row %) (sort-by :name stars ))]]
        (common-footer)]]))

(defn dashboard []
  (html
    [:html
      (common-head)
      [:body
        (nav-bar)
        [:div.container
          (breadcrumbs [["home" "/"] ["dashboard" "/dashboard"]])
          (page-header "Dashboard" (str "graphing luminosity for the stars"))
          [:div#mygraph]
        (common-footer)]]]))