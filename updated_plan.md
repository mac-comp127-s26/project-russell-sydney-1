# Updated Implementation Plan

## What Was Built (by May 4)

The core game loop is basically just:

- **Player physics** — gravity, per-frame velocity, bounce on platform landing
- **Platform generation** — procedural, infinite platforms that scroll up
- **Collision detection** — player lands on platforms only when falling (not when jumping through from below)
- **Camera scrolling** — camera follows player up; never moves back down
- **Screen wrapping** — player goes off the screen on one side and reappears on the other
- **Score** — based on maximum height reached
- **Game over** — triggered when player falls

All graphics are plain Kilt Graphics rectangles (green platforms, blue player square). No sprites yet.

## Known Limitations

- No sprites — player is a blue square, platforms are green rectangles
- No start screen — game begins immediately on launch
- No restart — must relaunch after game over
- All platforms are static (no moving, bouncing, or breaking platforms), which is not the case in the original game
- No enemies, power-ups, or jetpack (all in original game)
- No sound effects
- No high-score persistence

## Remaining Tasks (due May 9)

| Task | Priority |
|------|----------|
| Add restart on game over (press R or click) | High |
| Add a start/title screen | Medium |
| Replace blue square with a simple doodler sprite | Medium |
| Add at least one platform variant (e.g., moving platform) | Medium |
| Fill out README.md with screenshots, setup steps, limitations | High |
| Record 5–10 min presentation video | High |
| Write one-page presentation report | High |

## Responsibility

Russell — all tasks (solo project)
