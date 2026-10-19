package dev.iln.cobblemongo.data.game.updater

import fr.flowarg.flowupdater.download.DownloadList
import fr.flowarg.flowupdater.download.IProgressCallback
import fr.flowarg.flowupdater.download.Step

class UpdaterCallback(
    private val onUpdate: (DownloadList.DownloadInfo) -> Unit,
    private val onStepChange: (Step) -> Unit,
) : IProgressCallback {

    override fun step(step: Step) = onStepChange(step)

    override fun update(info: DownloadList.DownloadInfo) = onUpdate(info)
}