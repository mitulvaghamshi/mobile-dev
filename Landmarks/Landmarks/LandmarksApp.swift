//
//  LandmarksApp.swift
//  Landmarks
//
//  Created by mitulvaghamshi on 2021-07-13.
//

import SwiftUI

@main
struct LandmarksApp: App {
  @StateObject private var modelData = ModelData()
  
  var body: some Scene {
    let mainWindow = WindowGroup {
      ContentView().environmentObject(modelData)
    }

#if os(macOS)
    mainWindow.commands { LandmarkCommands() }
    Settings { LandmarkSettings() }
#else
    mainWindow
#endif

#if os(watchOS)
    WKNotificationScene(
      controller: NotificationController.self,
      category: "LandmarkNear"
    )
#endif
  }
}
