import 'package:flutter/material.dart';
import 'package:sphere/sphere.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(context) {
    return const MaterialApp(
      home: SphereView(),
      title: 'Flutter Sphere',
    );
  }
}

class SphereView extends StatefulWidget {
  const SphereView({super.key});

  @override
  SphereViewState createState() => SphereViewState();
}

class SphereViewState extends State<SphereView> {
  String surface = '2k_earth_daymap.jpg';
  final surfaces = [
    '2k_sun.jpg',
    '2k_mercury.jpg',
    '2k_venus_surface.jpg',
    '2k_earth_daymap.jpg',
    '2k_moon.jpg',
    '2k_mars.jpg',
    '2k_jupiter.jpg',
    '2k_saturn.jpg',
    '2k_uranus.jpg',
    '2k_neptune.jpg',
    'pano_1.jpg',
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        decoration: const BoxDecoration(
          image: DecorationImage(
            image: AssetImage('assets/2k_stars.jpg'),
            fit: BoxFit.cover,
          ),
        ),
        child: Column(children: <Widget>[
          Expanded(
            child: Sphere(
              latitude: 15,
              longitude: 0,
              key: ValueKey(surface),
              alignment: Alignment.center,
              surface: 'assets/$surface',
              radius: MediaQuery.of(context).size.width / 2.0 - 10.0,
            ),
          ),
          SizedBox(
            height: 120,
            child: ListView.builder(
              itemExtent: 200,
              itemCount: surfaces.length,
              scrollDirection: Axis.horizontal,
              itemBuilder: (context, index) {
                final item = surfaces[index];
                return ListTile(
                  textColor: Colors.white,
                  onTap: () => setState(() => surface = item),
                  title: Column(children: [
                    Image.asset('assets/thumb/$item'),
                    Text(item),
                  ]),
                );
              },
            ),
          ),
        ]),
      ),
    );
  }
}
