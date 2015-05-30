(ns star-sounds.views
  (:require [hiccup.core :refer :all]
            [star-sounds.global :as g]))

(def bs-columns ["col-sm-1","col-sm-2","col-sm-3","col-sm-4","col-sm-5","col-sm-6","col-sm-7","col-sm-8","col-sm-9","col-sm-10","col-sm-11","col-sm-12"])

(defn common-head [& extras]
  [:head
    [:title @g/brand-name]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
    [:link {:href "/static/bootstrap/css/bootstrap.min.css" :rel "stylesheet" :media "screen"}]
    [:script {:src "http://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"}]
    [:script {:src "http://mohayonao.github.io/timbre.js/timbre.js"}]
    [:script {:src "/static/bootstrap/js/bootstrap.min.js"}]
    [:script {:src "/static/js/stars.js"}]
    [:script {:src "/static/js/vis/dist/vis.js"}]
    [:link {:href "/static/bootstrap/css/lavish-theme.css" :rel "stylesheet" :media "screen"}]
    [:link {:href "/static/css/star-sounds.css" :rel "stylesheet" :media "screen"}]
    extras])

(defn nav-bar []
  [:div.navbar.navbar-inverse.navbar-fixed-top {:role "navigation"}
    [:div.container
      [:div.navbar-header
        [:a.navbar-brand {:href "/"} @g/brand-name]]
      [:div.collapse.navbar-collapse
        [:ul.nav.navbar-nav.navbar-right
          [:li [:a {:href "/allstars"} "All Stars" ]]
          [:li [:a {:href "/navigate"} "Navigate" ]]]]]]  )

[:a.btn.btn-primary {:href "/db"} "Let's get started..."]

(defn jumbotron [title sub-title]
  [:div.jumbotron
    [:h1 title]
    [:p sub-title]])

(defn page-header [text]
  [:div.page-header
    [:h1 text]])

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
    [:span.glyphicon.glyphicon-play-circle] (str "&nbsp;" value)])

(defn star-row [star]
  [:tr 
    [:td (get star :id)]
    [:td (get star :label)]
    [:td (str "(" (get star :x) ", " (get star :y) ", " (get star :z) ")" )]
    (td-sound (get star :distly))
    (td-sound (get star :lum))
    (td-sound (get star :colorb_v))
    (td-sound (get star :speed))
    (td-sound (get star :absmag))
    (td-sound (get star :appmag))])

(defn welcome-view []
  (html
    [:html
      (common-head)
      [:body
        (nav-bar)
        [:div.container
          (breadcrumbs [["home" "/"]])
          (jumbotron (str "Welcome to " @g/brand-name "!") "An simple web app that allows you to audibly explore the heavens.")
        (common-footer)]]]))

(defn allstars [stars]
  (html
    [:html
      (common-head)
      [:body
        (nav-bar)
        [:div.container
          (breadcrumbs [["home" "/"] ["all stars" "/allstars"]])
          (page-header "Raw data for the stars")
          [:table.table-hover.table-bordered.table-condensed {:style "width: 100%"}
            [:thead
              [:th "Id"]
              [:th "Name"]
              [:th "Coords"]
              [:th "Distance"]
              [:th "Luminosity"]
              [:th "Color"]
              [:th "Speed"]
              [:th "Abs Mag"]
              [:th "App Mag"]]
            [:tbody
              (map #(star-row %) (sort-by :id stars))]]
        (common-footer)]]]))

(defn navigate []
  (html
    [:html
      (common-head)
      [:body
        (nav-bar)
        [:div.container
          (breadcrumbs [["home" "/"] ["navigate" "/navigate"]])
          (page-header "Navigate the stars")
          [:div#mygraph ]
        (common-footer)]]]))