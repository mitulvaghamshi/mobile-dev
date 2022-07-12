//
//  CategoryHome.swift
//  Landmarks
//
//  Created by mitulvaghamshi on 2021-10-04.
//

import SwiftUI

struct CategoryHome: View {
  @EnvironmentObject var modelData: ModelData
  @State private var showingProfile = true
  
  var body: some View {
    NavigationView {
      List {
        //        modelData.features.first!.image
        //          .resizable()
        //          .scaledToFill()
        //          .frame(height: 180)
        //          .listRowInsets(EdgeInsets())
        PageView(pages: modelData.features.map({
          FeatureCard(landmark: $0)
        }))
          .aspectRatio(3 / 2, contentMode: .fit)
          .listRowInsets(EdgeInsets())
        
        ForEach(modelData.categories.keys.sorted(), id: \.self) { key in
          CategoryRow(
            categoryName: key,
            items: modelData.categories[key]!
          )
        }.listRowInsets(EdgeInsets())
      }
      //            .listStyle(InsetListStyle())
      .navigationTitle("Featured")
      .toolbar {
        Button(action: { showingProfile.toggle() }) {
          Image(systemName: "person.crop.circle")
            .accessibilityLabel("User Profile")
        }
      }
      .sheet(isPresented: $showingProfile) {
        ProfileHost().environmentObject(modelData)
      }
    }
  }
}

struct CategoryHome_Previews: PreviewProvider {
  static var previews: some View {
    CategoryHome()
      .environmentObject(ModelData())
  }
}
