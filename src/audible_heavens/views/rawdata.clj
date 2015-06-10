(ns audible_heavens.views.rawdata
  (:require [hiccup.core :refer :all]
            [audible_heavens.global :as global]
            [audible_heavens.views.views :as view]
            [audible_heavens.data :as data]))

(defn sort-buttons [k attributes]
  [:div 
    [:h4 "Sort By"]
    [:div.button-group {:id (subs (str k "-sorts") 1)}
      [:button.btn.btn-default.btn-primary {:data-sort-by "original-order"} "original order"]
      [:button.btn.btn-default {:data-sort-by "name"} "name"]
      (for [attribute attributes] 
        [:button.btn.btn-default {:data-sort-by (subs (str (key attribute)) 1)} (clojure.string/lower-case (val attribute))])]])

(defn data-item [k value label norm]
  [:div {:data-sound norm} 
    [:i.clickable.glyphicon.glyphicon-music] 
    "&nbsp;&nbsp;" 
    [:span.text-info  (str label ": ")] 
    [:span {:class (subs (str k " text-success") 1)} value]])

(defn data-row [data thresholds attributes]
  [:div.isotope-data-item.panel.panel-info
    [:div.panel-heading
      [:h4.name (get data :label)]]
    [:div.panel-body  
      [:small.coords (str "(" (get data :x) ", " (get data :y) ", " (get data :z) ")" )]
      (for [attrib attributes
        :let [k (key attrib)
              v (get data k)
              lbl (val attrib)
              thres (get thresholds k)
              max (get thres :max)
              min (get thres :min)
              norm (data/normalize-value v max min)]]
          (data-item k v lbl norm))]])

(defn display-data [data]
  (let [all-data (val data)
        data-key (key data)
        data-values (get all-data :data)
        attributes (get all-data :attributes)
        thresholds (get all-data :thresholds)]
    [:div
      (sort-buttons data-key attributes)
      [:br]
      [:div.isotope-data {:id data-key}
        (for [item data-values]
          (data-row item thresholds attributes))]]))

(defn get-counts [data]
  (count (get data :data)))

(defn index [data]
  (html
    [:html
      (view/common-head)
      [:body
        (view/nav-bar)
        [:div.container
          ; (view/breadcrumbs [["home" "/"] ["all stars" "/allstars"]])
          (let [star-count (get-counts (get data :stars))
                exoplanet-count (get-counts (get data :exoplanets))
                galaxy-count (get-counts (get data :galaxies))
                cluster-count (get-counts (get data :clusters))
                sub-title (str star-count " stars, " exoplanet-count " exoplanets, " galaxy-count " galaxies, and " cluster-count " clusters")]
            (view/page-header "All Heavenly Data" sub-title))
          [:ul#data-tabs.nav.nav-tabs.nav-pills
            [:li.active [:a {:href "#stars" :data-toggle "tab"} "Stars"]]
            [:li [:a {:href "#exoplanets" :data-toggle "tab"} "Exoplanets"]]
            [:li [:a {:href "#galaxies" :data-toggle "tab"} "Galaxies"]]
            [:li [:a {:href "#clusters" :data-toggle "tab"} "Star Clusters"]]]
          [:div#tab-content.tab-content 
            (for [datum data]
              [:div.tab-pane {:id (key datum)} (display-data datum)])]]
        (view/common-footer)]
        [:script {:src "http://cdnjs.cloudflare.com/ajax/libs/jquery.isotope/2.2.0/isotope.pkgd.min.js"}]        
        [:script {:src "/static/js/stars.js"}]
        [:script {:src "/static/js/audio.js"}]]))