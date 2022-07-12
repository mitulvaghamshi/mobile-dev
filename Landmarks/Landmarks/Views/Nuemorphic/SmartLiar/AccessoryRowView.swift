import SwiftUI

struct AccessoryRowView: View {
  var items: [AccessoryView]
  
  var body: some View {
    ScrollView(.horizontal, showsIndicators: false) {
      HStack {
        ForEach(items, id: \.uuid) { accessory in
          accessory.padding(.horizontal, 30)
        }
      }
      .padding(.top, 32)
      .padding(.bottom, 38)
    }
  }
}

struct AccessoryRowView_Previews: PreviewProvider {
  static var previews: some View {
    AccessoryRowView(items: [
      AccessoryView(title: "Control Room", image: Image(systemName: "lightbulb")),
      AccessoryView(title: "Dungeon", image: Image(systemName: "lock.shield")),
      AccessoryView(title: "Laser", image: Image(systemName: "scope")),
      AccessoryView(title: "Periscope", image: Image(systemName: "eye"))
    ])
  }
}
