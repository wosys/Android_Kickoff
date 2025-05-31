/*
 * Copyright 2023-2024 wintmain
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package lib.wintmain.xplayer.play

/**
 * des 所有的具体Player必须实现该接口,目的是为了让PlayManager不依赖任何
 *     具体的音频播放实现,原因大概有两点
 *
 *     1.PlayManager包含业务信息,Player不应该与业务信息进行耦合,否则每次改动都会对业务造成影响
 *
 *     2.符合开闭原则,如果需要对Player进行替换势必会牵连到PlayManager中的业务,因而造成不必要的麻烦
 *       如果基于IPlayer接口编程,扩展出一个Player即可,正所谓对扩展开放、修改关闭
 */
interface IPlayer {

    /**
     * 将IPlayStatus注入到PlayController内,做状态监听
     */
    fun setPlayStatus(iPlayStatus: IPlayerStatus)

    /**
     * 播放新的音频
     * @param path 本地路径
     */
    fun play(path: String)

    /**
     * 播放
     */
    fun resume()

    /**
     * 暂停
     */
    fun pause()

    /**
     * 停止播放,释放播放内容
     */
    fun stop()

    /**
     * 跳转播放
     */
    fun seekTo(duration: Int)

    /**
     * 重置
     */
    fun reset()

    /**
     * 释放内存,播放器置空
     */
    fun release()

    /**
     * 是否正在播放
     */
    fun isPlaying(): Boolean

    /**
     *  获取播放进度
     */
    fun getProgress(): Int
}