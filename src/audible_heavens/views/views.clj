(ns audible_heavens.views.views
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
    [:script {:src "http://cdnjs.cloudflare.com/ajax/libs/jquery.isotope/2.2.0/isotope.pkgd.min.js"}]
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
          [:li [:a {:href "/rawdata"} "Raw Data" ]]
          [:li [:a {:href "/dashboard"} "Dashboard" ]]]]]])

(defn jumbotron [title sub-title]
  [:div.jumbotron
    [:h1 title]
    [:p sub-title]])

(defn page-header [text sub-text]
  [:div.page-header
    [:h2 text [:br] [:small sub-text]]])

(defn panel [title & body]
  [:div.panel.panel-primary
    [:div.panel-heading
      [:h3.panel-title title]]
    [:div.panel-body body]])

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