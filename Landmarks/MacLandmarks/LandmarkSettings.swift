//
//  LandmarkSettings.swift
//  Landmarks
//
//  Created by mitulvaghamshi on 2021-10-15.
//

import SwiftUI

struct LandmarkSettings: View {
  @AppStorage("MapView.zoom")
  private var zoom: MapView.Zoom = .medium

  var body: some View {
    Form {
      Picker("Map Zoom", selection: $zoom) {
        ForEach(MapView.Zoom.allCases) { level in
          Text(level.rawValue)
        }
      }.pickerStyle(InlinePickerStyle())
    }
    .frame(width: 200)
    .navigationTitle("Landmark Settings")
    .padding()
  }
}

struct LandmarkSettings_Previews: PreviewProvider {
  static var previews: some View {
    LandmarkSettings()
  }
}
