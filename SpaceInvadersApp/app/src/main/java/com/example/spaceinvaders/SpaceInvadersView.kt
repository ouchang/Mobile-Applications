package com.example.spaceinvaders

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class SpaceInvadersView(initContext : Context, x : Int, y : Int) : SurfaceView(initContext), Runnable {
    lateinit var gameThread : Thread
    lateinit var surfaceHolder : SurfaceHolder
    lateinit var mContext : Context

    var playing : Boolean = false // is game running?
    var paused : Boolean = true // game is paused at the start

    lateinit var canvas : Canvas
    lateinit var paint : Paint

    var fps : Long = 0 // game frame rate
    var timeFrame : Long = 0

    // size of screen in pixels
    var screenX : Int = 0
    var screenY : Int = 0

    lateinit var playerShip : PlayerShip
    lateinit var bullet : Bullet

    lateinit var invadersBullets : Array<Bullet> // invaders' bullets
    var nextBullet : Int = 0
    var maxInvaderBullets : Int = 10

    lateinit var invaders : Array<Invader> // invaders (max 60)
    var numOfInvaders : Int = 0

    lateinit var bricks : Array<DefenceBrick> // player's shelters are build from bricks
    var numOfBricks : Int = 0

    var lives : Int = 3
    var score : Int = 0

    init {
        mContext = initContext
        surfaceHolder = holder
        paint = Paint()

        screenX = x
        screenY = y

        invaders = Array(40) { Invader(context, 100, 100, 100 , 100) }
        invadersBullets = Array(200) { Bullet(100) }
        bricks = Array(400) { DefenceBrick(100, 100, 100, 100, 100) }

        prepareLevel()
    }

    fun prepareLevel() {
        playerShip = PlayerShip(mContext, screenX, screenY)
        bullet = Bullet(screenY)

        for(i in 0 until invadersBullets.size step 1) {
            invadersBullets[i] = Bullet(screenY)
        }

        // build army of invaders
        numOfInvaders = 0
        for(column in 0 until invaders.size/5 step 1) {
            for(row in 0 until 5 step 1) {
                invaders[numOfInvaders] = Invader(mContext, row, column, screenX, screenY)
                numOfInvaders++
            }
        }

        numOfBricks = 0
        for(shelterNum in 0 until 4 step 1) {
            for(column in 0 until 10 step 1) {
                for(row in 0 until 5 step 1) {
                    bricks[numOfBricks] = DefenceBrick(row, column, shelterNum, screenX, screenY)
                    numOfBricks++
                }
            }
        }
    }

    override fun run() {

        while(playing) {
            var startFrameTime : Long = System.currentTimeMillis() // ?

            if(!paused) {
                update()
            }

            draw()

            timeFrame = System.currentTimeMillis() - startFrameTime
            if(timeFrame >= 1) {
                fps = 1000 / timeFrame
            }
        }
    }

    fun update() {
        var bumped : Boolean = false
        var lost : Boolean = false

        if(lost) {
            prepareLevel()
        }

        playerShip.update(fps)

        if(bullet.isActive) {
            bullet.update(fps)
        }

        for(i in 0 until invadersBullets.size step 1) {
            if(invadersBullets[i].isActive) {
                invadersBullets[i].update(fps)
            }
        }

        for(i in 0 until numOfInvaders step 1) {
            if(invaders[i].isVisible) {
                invaders[i].update(fps)

                if(invaders[i].takeAim(playerShip.x, playerShip.length)) {
                    if(invadersBullets[nextBullet].shoot(invaders[i].x + invaders[i].length/2, invaders[i].y, bullet.DOWN)) {
                        nextBullet++

                        if(nextBullet == maxInvaderBullets) {
                            nextBullet = 0
                        }
                    }
                }

                if((invaders[i].x > screenX - invaders[i].length) || (invaders[i].x < 0)) {
                    bumped = true
                }
            }
        }

        if(bumped) {
            for(i in 0 until numOfInvaders step 1) {
                invaders[i].dropDownAndReverse()

                if(invaders[i].y > screenY - screenY / 10) {
                    lost = true
                }
            }
        }

        if(bullet.getImpactPointY() < 0) {
            bullet.isActive = false
        }

        for(i in 0 until invadersBullets.size step 1) {
            if(invadersBullets[i].getImpactPointY() > screenY) {
                invadersBullets[i].isActive = false
            }
        }

        // player hit an invader
        if(bullet.isActive) {
            for(i in 0 until numOfInvaders step 1) {
                if(invaders[i].isVisible) {
                    if(RectF.intersects(bullet.rect, invaders[i].rect)) {
                        invaders[i].isVisible = false
                        bullet.isActive = false
                        score += 10

                        // player won
                        if(score == numOfInvaders * 10) {
                            paused = true
                            //score = 0
                            lives = 3
                            //prepareLevel()
                        }
                    }
                }
            }
        }

        // alien hit shelter
        for(i in 0 until invadersBullets.size step 1) {
            if(invadersBullets[i].isActive) {
                for(j in 0 until numOfBricks step 1) {
                    if(bricks[j].isVisible) {
                        if(RectF.intersects(invadersBullets[i].rect, bricks[j].rect)) {
                            invadersBullets[i].isActive = false
                            bricks[j].isVisible = false
                        }
                    }
                }
            }
        }

        // player hit shelter
        if(bullet.isActive) {
            for(i in 0 until numOfBricks step 1) {
                if(RectF.intersects(bullet.rect, bricks[i].rect)) {
                    bullet.isActive = false
                    bricks[i].isVisible = false
                }
            }
        }

        // invader hit player ship
        for(i in 0 until invadersBullets.size step 1) {
            if(invadersBullets[i].isActive) {
                if(RectF.intersects(playerShip.rect, invadersBullets[i].rect)) {
                    invadersBullets[i].isActive = false
                    lives--

                    // game over
                    /*
                    if(lives == 0) {
                        paused = true
                        lives = 3
                        score = 0
                        //prepareLevel()

                        paint.setColor(Color.parseColor("#FFE800"))
                        paint.textSize = 100f
                        canvas.drawText("YOU'VE LOST!", 100f, 50f, paint)
                    }
                    */
                }
            }
        }
    }

    fun draw() {
        if(surfaceHolder.surface.isValid) {
            canvas = surfaceHolder.lockCanvas()

            canvas.drawColor(Color.parseColor("#192524"))

            paint.setColor(Color.parseColor("#F46D75"))
            paint.textSize = 80f
            canvas.drawText("Score: " + score, 10f, 100f, paint)

            canvas.drawBitmap(playerShip.bitmap, playerShip.x, (screenY - 50).toFloat(), paint)

            // player ship shoots
            if(bullet.isActive) {
                paint.setColor(Color.parseColor("#FFE800"))
                canvas.drawRect(bullet.rect, paint)
            }

            // invader shoots
            for(i in 0 until invadersBullets.size step 1) {
                if(invadersBullets[i].isActive) {
                    paint.setColor(Color.parseColor("#FF4747"))
                    canvas.drawRect(invadersBullets[i].rect, paint)
                }
            }

            for(i in 0 until numOfInvaders step 1) {
                if (invaders[i].isVisible) {
                    canvas.drawBitmap(invaders[i].bitmap1, invaders[i].x, invaders[i].y, paint)
                }
            }

            for(i in 0 until numOfBricks step 1) {
                if(bricks[i].isVisible) {
                    paint.setColor(Color.parseColor("#86DC3D"))
                    canvas.drawRect(bricks[i].rect, paint)
                }
            }

            if(score == numOfInvaders*10) {
                paint.setColor(Color.parseColor("#FFE800"))
                paint.textSize = 100f
                canvas.drawText("YOU'VE WON!", 700f, 500f, paint)
                surfaceHolder.unlockCanvasAndPost(canvas)


                Thread.sleep(1000)
                score = 0
                prepareLevel()
            } else {
                surfaceHolder.unlockCanvasAndPost(canvas)
            }
        }
    }

    fun pause() {
        playing = false
        gameThread.join()
    }

    fun resume() {
        playing = true
        gameThread = Thread(this)
        gameThread.start()
    }

    override fun onTouchEvent(motionEvent: MotionEvent) : Boolean {

        when (motionEvent.action) {
            // player touched the screen
            MotionEvent.ACTION_DOWN -> {
                paused = false

                if(motionEvent.y > screenY - screenY / 8) {
                    if(motionEvent.x > screenX / 2) {
                        playerShip.setMovementState(playerShip.RIGHT)
                    } else {
                        playerShip.setMovementState(playerShip.LEFT)
                    }
                }

                if(motionEvent.y < screenY - screenY / 8) {
                    // shot fired
                    bullet.shoot(playerShip.x + playerShip.length/2, screenY.toFloat(), bullet.UP)
                }
            }

            // player removed finger from the screen
            MotionEvent.ACTION_UP -> {
                if(motionEvent.y  > screenY - screenY  / 10) {
                    playerShip.setMovementState(playerShip.STOPPED)
                }
            }
        }
        return true
    }
}