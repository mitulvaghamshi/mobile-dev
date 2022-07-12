//
//  ProfileHost.swift
//  Landmarks
//
//  Created by mitulvaghamshi on 2021-10-04.
//

import SwiftUI

struct ProfileHost: View {
  @Environment(\.editMode) var editMode
  @EnvironmentObject var modelData: ModelData
  @State private var draftProfile = Profile.default

  var body: some View {
    VStack {
      HStack {
        if editMode?.wrappedValue == .active {
          Button("Cancel") {
            draftProfile = modelData.profile
            editMode?.animation().wrappedValue = .inactive
          }
        }
        Spacer()
        EditButton()
      }
      if editMode?.wrappedValue == .inactive {
        ProfileSummary(profile: modelData.profile)
      } else {
        ProfileEditor(profile: $draftProfile)
          .onAppear(perform: { draftProfile = modelData.profile })
          .onDisappear(perform: { modelData.profile = draftProfile })
      }
    }.padding()
  }
}

struct ProfileHost_Previews: PreviewProvider {
  static var previews: some View {
    ProfileHost().environmentObject(ModelData())
  }
}
