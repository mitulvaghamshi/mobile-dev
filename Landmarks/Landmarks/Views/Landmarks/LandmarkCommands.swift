//
//  LandmarkCommands.swift
//  Landmarks
//
//  Created by mitulvaghamshi on 2021-10-15.
//

import SwiftUI

struct LandmarkCommands: Commands {
  private struct MenuContent: View {
    @FocusedBinding(\.selectedLandmarks) var selectedLandmark

    var body: some View {
      Button("\(selectedLandmark?.isFavorite == true ? "Remove" : "Mark as") Favorite") {
        selectedLandmark?.isFavorite.toggle()
      }
      .keyboardShortcut("f", modifiers: [.shift, .option])
      .disabled(selectedLandmark == nil)
    }
  }

  var body: some Commands {
    SidebarCommands()
    CommandMenu("Landmark") {
      MenuContent()
    }
  }
}

private struct SelectedLandmarkKey: FocusedValueKey {
  typealias Value = Binding<Landmark>
}

extension FocusedValues {
  var selectedLandmarks: Binding<Landmark>? {
    get { self[SelectedLandmarkKey.self] }
    set { self[SelectedLandmarkKey.self] = newValue }
  }
}
