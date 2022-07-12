//
//  LandmarkList.swift
//  WatchLandmarks WatchKit Extension
//
//  Created by mitulvaghamshi on 2021-10-14.
//

import SwiftUI

struct LandmarkList: View {
  @EnvironmentObject var modelData: ModelData
  @State private var showFavoritesOnly = false

  var filteredlandmarks: [Landmark] {
    modelData.landmarks.filter { landmark in
      (!showFavoritesOnly || landmark.isFavorite)
    }
  }

  var body: some View {
    NavigationView {
      List {
        Toggle(isOn: $showFavoritesOnly, label: {
          Text("Favorites only")
        })
        ForEach(filteredlandmarks) { landmark in
          NavigationLink(destination: LandmarkDetail(landmark: landmark)) {
            LandmarkRow(landmark: landmark)
          }
        }
      }
      .navigationTitle("Landmarks")
    }
  }
}

struct LandmarkList_Previews: PreviewProvider {
  static var previews: some View {
    LandmarkList().environmentObject(ModelData())
  }
}
