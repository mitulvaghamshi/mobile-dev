import SwiftUI

struct ProgressBarView: View {
  var title: String
  @Binding var percent: Double
  
  var body: some View {
    GeometryReader { geometry in
      VStack {
        HStack {
          Text(self.title)
            .foregroundColor(.lairDarkGray)
            .bold()
          Spacer()
          Text("\(Int(self.percent * 100))%")
            .foregroundColor(.lairDarkGray)
            .bold()
        }
        ZStack(alignment: .leading) {
          ZStack {
            Capsule()
              .frame(height: 14)
              .foregroundColor(Color(white: 0.8))
            LinearGradient.lairHorizontalDarkToLight
              .frame(height: 14)
              .mask(Capsule())
              .opacity(0.7)
          }
          
          ZStack {
            LinearGradient.lairHorizontalLight
              .frame(
                width: (geometry.size.width - 32) * CGFloat(self.percent),
                height: 10)
              .mask(
                Capsule()
                  .padding(.horizontal, 2)
            )
            LinearGradient.lairVerticalLightToDark
              .frame(
                width: (geometry.size.width - 32) * CGFloat(self.percent),
                height: 10)
              .mask(
                Capsule()
                  .padding(.horizontal, 2)
            )
              .opacity(0.7)
          }
          .shadow(color: Color.lairShadowGray.opacity(0.5), radius: 2, x: 0, y: 1)
        }
        .clipShape(Capsule())
      }
      .padding(.horizontal)
    }
  }
}

struct ProgressBarView_Previews: PreviewProvider {
  static var previews: some View {
    ProgressBarView(title: "Laser Charge", percent: .constant(0.42))
  }
}
