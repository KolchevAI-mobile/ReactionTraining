# Reaction Training Game

[![Platform](https://img.shields.io/badge/Platform-Android%20%2B%20KMP-green.svg)](https://kotlinlang.org/docs/multiplatform.html)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.0+-purple.svg)](https://kotlinlang.org/)

Мини‑игра для тренировки скорости реакции.  
Жмёшь старт, ждёшь случайный интервал и как можно быстрее реагируешь, когда «светофор» даёт сигнал.

## Технологический стек
- **Kotlin Multiplatform (KMP)**: модуль `shared` — домен, движок и UI на [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform).
- **Android**: `ComponentActivity` + Jetpack (ViewModel, `activity-compose`, `lifecycle-runtime-compose`).
- **Архитектура**: логика в `ReactionGameEngine`, экран `GameScreen` в `commonMain`, `GameViewModel` в `app` связывает `StateFlow` с Compose.

## Сборка
- **Android (Windows / Linux)**: как обычный Gradle‑проект, например `:app:assembleDebug`.
- **iOS (macOS)**: в `shared` подключены `iosArm64` / `iosSimulatorArm64` при сборке на Apple‑хосте; отдельного Xcode‑таргета в репозитории пока нет — при необходимости его можно добавить по [документации KMP](https://kotlinlang.org/docs/multiplatform-get-started.html).

## Игровая механика
1. Пользователь нажимает кнопку (Begin / Waiting / Stop в зависимости от фазы).
2. В фазе ожидания последовательно загораются индикаторы, затем — случайная задержка до сигнала.
3. После сигнала идёт отсчёт миллисекунд; повторное нажатие возвращает в idle с сохранением последнего времени (если игра была в ходе).

### Реализовано
- защита от лишних запусков в одной и той же фазе;
- случайное окно задержки перед реакцией;
- визуал «светофора» в Compose (круги и цвета из темы).

## Структура модулей
| Модуль | Содержимое |
|--------|------------|
| `shared` | `commonMain`: движок, модели, `GameScreen`, `ReactionTrainingTheme` |
| `app`  | `MainActivity`, `GameViewModel`, манифест, иконки, строки для лейбла приложения |

## Экран (скриншоты из ранних версий на XML, логика та же)

Состояния до старта, ожидания и игры: см. изображения в истории репозитория.
