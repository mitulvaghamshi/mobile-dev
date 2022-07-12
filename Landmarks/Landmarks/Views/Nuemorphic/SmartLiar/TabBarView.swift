import SwiftUI

struct TabBarView: View {
  @Binding var selectedItem: SmartView
  
  var tabBarItems: [TabBarItemView]
  
  var body: some View {
    HStack {
      ForEach(tabBarItems, id: \.uuid) { item in
        HStack {
          Spacer()
          item
          Spacer()
        }
      }
    }
    .padding(.top, 11)
    .padding(.bottom, 22)
    
  }
}

struct TabBarView_Previews: PreviewProvider {
  static var previews: some View {
    TabBarView(
      selectedItem: .constant(.lair),
      tabBarItems: [
        TabBarItemView(
          selectedItem: .constant(.lair),
          smartView: .lair,
          icon: "pencil.tip"),
        TabBarItemView(
          selectedItem: .constant(.lair),
          smartView: .camera,
          icon: "video.circle"),
        TabBarItemView(
          selectedItem: .constant(.lair),
          smartView: .automation,
          icon: "alarm"),
        TabBarItemView(
          selectedItem: .constant(.lair),
          smartView: .settings,
          icon: "gear")
      ]
    )
  }
}

