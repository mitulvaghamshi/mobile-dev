//
//  NotificationView.swift
//  WatchLandmarks WatchKit Extension
//
//  Created by mitulvaghamshi on 2021-10-14.
//

import SwiftUI

struct NotificationView: View {
  var title: String?
  var message: String?
  var landmark: Landmark?

  var body: some View {
    VStack {
      if landmark != nil {
        CircleImage(image: landmark!.image.resizable())
          .scaledToFit()
      }

      Text(title ?? "Unknown Landmark")
        .font(.headline)

      Divider()

      Text(message ?? "You are within five miles of your favorite landmarks.")
        .font(.caption)

    }
    .lineLimit(0)
    .padding(16)
  }
}

struct NotificationView_Previews: PreviewProvider {
  static var previews: some View {
    Group {
      NotificationView(
        title: "Landmark",
        message: "Notification",
        landmark: ModelData().landmarks.first!
      )
      NotificationView()
    }
  }
}
