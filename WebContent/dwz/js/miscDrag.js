/**
 * @author 寮犳収鍗� z@j-ui.com
 */
(function ($) {
    DWZ.miscDrag = {
        start: function ($sortBox, $item, event, op) {

            var $helper = $item.clone();
            var position = $item.position();

            $helper.addClass('sortDragHelper').css({
                position: 'absolute',
                top: position.top + $sortBox.scrollTop(),
                left: position.left,
                zIndex: op.zIndex,
                minWidth: $item.width() + 'px',
                height: $item.height() + 'px'
            }).jDrag({
                drag: this.drag,
                stop: this.stop,
                event: event
            });
            $helper.data('$sortBox', $sortBox);

            $item.before($helper);
            return false;
        },
        drag: function (el, event) {
        },
        stop: function (el, event) {
            var $helper = $(arguments[0]),
                $sortBox = $helper.data('$sortBox'),
                $overBox = DWZ.miscDrag._getOverSortBox($sortBox.find($sortBox.attr('drag-rel')), $helper);

            if ($overBox.length > 0) { //绉诲姩鍒版寚瀹氬鍣�

                var $dragBox = $helper.appendTo($overBox).mousedown(function (event) {
                    $(this).jDrag({event: event});
                });

                var txt = $dragBox.html(),
                    icon = $dragBox.attr('data-icon'),
                    sn = $dragBox.attr('data-sn'),
                    sequence = $overBox.find('> div').size();

                var overBoxPos = $overBox.position(),
                    dragBoxPos = $dragBox.position();

                var content = icon ? '<img src="' + icon + '" />' : txt;
                $dragBox.attr('data-sequence', sequence).html(' <h2>' + sequence + '</h2>' + content).css({
                    height: 'auto',
                    top: (dragBoxPos.top - overBoxPos.top) + 'px',
                    left: (dragBoxPos.left - overBoxPos.left) + 'px'
                });

                var rel = $sortBox.attr('rel');
                if (rel) {
                    $('<div class="sortDrag" data-sn="' + sn + '"><h2>' + sequence + '</h2></div>').appendTo(rel);
                }
            } else {
                $helper.remove();
            }
        },

        _getOverSortBox: function ($sortBox, $item) {
            var itemPos = $item.position();
            var y = itemPos.top + ($item.height() / 2), x = itemPos.left + ($item.width() / 2);
            return $sortBox.filter(':visible').filter(function () {
                var $sortBox = $(this), sortBoxPos = $sortBox.offset(),
                    sortBoxH = $sortBox.height(), sortBoxW = $sortBox.width();
                return DWZ.isOver(y, x, sortBoxPos.top, sortBoxPos.left, sortBoxH, sortBoxW);
            });
        },
        _createPlaceholder: function ($item) {
            return $('<' + $item[0].nodeName + ' class="sortDragPlaceholder"/>').css({
                // width:$item.outerWidth()+'px',
                height: $item.outerHeight() + 'px',
                marginTop: $item.css('marginTop'),
                marginRight: $item.css('marginRight'),
                marginBottom: $item.css('marginBottom'),
                marginLeft: $item.css('marginLeft')
            });
        },
        startSortDrag: function ($sortBox, $item, event, op) {
            var $placeholder = this._createPlaceholder($item);
            var $helper = $item.clone();
            var position = $item.position();
            $helper.data('$sortBox', $sortBox).data('op', op).data('$item', $item).data('$placeholder', $placeholder);
            $helper.addClass('sortDragHelper').css({
                position: 'absolute',
                top: position.top + $sortBox.scrollTop(),
                left: position.left,
                zIndex: op.zIndex,
                width: $item.width() + 'px',
                height: $item.height() + 'px'
            }).jDrag({
                drag: this.dragSortDrag,
                stop: this.stopSortDrag,
                event: event
            });

            $item.before($helper).before($placeholder);
            return false;
        },
        dragSortDrag: function (el, event) {
            var $helper = $(arguments[0]), $sortBox = $helper.data('$sortBox'),
                $placeholder = $helper.data('$placeholder');

            // 淇鍑虹幇婊氬姩鏉℃嫋鎷戒綅缃�
            var $unitBox = $helper.parents(".unitBox:first"),
                position = $helper.offset();
            $helper.css({
                top: position.top + $unitBox.scrollTop()
            });

            var $dragList = $($sortBox.attr('drag-rel'));

            for (var i = 0; i < $dragList.size(); i++) {

                var $overBox = DWZ.miscDrag._getOverSortBox($dragList.eq(i), $helper);

                if ($overBox.length > 0 && $overBox[0] != $sortBox[0]) { //绉诲姩鍒板叾浠栧鍣�
                    $placeholder.appendTo($overBox);
                }
            }

        },
        stopSortDrag: function () {
            var $helper = $(arguments[0]), $sortBox = $helper.data('$sortBox'),
                $placeholder = $helper.data('$placeholder'), $item = $helper.data('$item');

            if ($placeholder && $placeholder.is(':visible')) {
                var position = $placeholder.offset();

                $helper.animate({
                        top: (position.top + $sortBox.scrollTop()) + "px",
                        left: position.left + "px"
                    },
                    {
                        complete: function () {
                            //澶嶅埗鍒扮洰鏍囧鍣�
                            var $destBox = $placeholder.parents(".sortDrag:first");
                            //var html = $helper.html()+'<span class="close">脳</span>';
                            var html = $helper.html();

                            $destBox.append('<div class="dragItem" data-sn="' + $helper.attr('data-sn') + '">' + html + '</div>');

                            $placeholder.remove();
                            $helper.remove();
                            if ($sortBox.attr('data-duplicate') != 1) {
                                $item.remove();
                            }

                            //浠庢柊缁戝畾sortDrag
                            $destBox.sortDrag({refresh: true});
                        },
                        duration: 300
                    }
                );
            } else {
                $placeholder.remove();
                $helper.remove();
            }

        }
    };

    $.fn.extend({
        miscDrag: function (options) {
            var op = $.extend({
                cursor: 'move', // selector 鐨勯紶鏍囨墜鍔�
                sortBoxs: 'div.miscDrag', //鎷栧姩鎺掑簭椤圭埗瀹瑰櫒
                replace: false, //2涓猻ortBox涔嬮棿鎷栧姩鏇挎崲
                items: '> dt .dragBox', //鎷栧姩鎺掑簭椤归€夋嫨鍣�
                zIndex: 1000
            }, options);

            return this.each(function () {
                var $box = $(this);
                $box.find(op.items).each(function (i) {
                    var $item = $(this);
                    $item.mousedown(function (event) {
                        DWZ.miscDrag.start($box, $item, event, op);

                        event.preventDefault();
                    });
                });
            });
        },
        miscDragData: function () {
            var $miscDrag = $(this),
                $miscSortDrag = $($miscDrag.attr('rel')),
                $dragBoxList = $miscDrag.find('dd .dragBox'),
                $sortDragList = $miscSortDrag.find('.sortDrag');

            var data = [];
            for (var i = 0; i < $dragBoxList.size(); i++) {
                var $dragBox = $dragBoxList.eq(i), $sortDrag = $sortDragList.eq(i),
                    $dragBoxPos = $dragBox.position();

                var dataItem = {
                    sn: $dragBox.attr('data-sn'),
                    sequence: $dragBox.attr('data-sequence'),
                    top: parseInt($dragBoxPos.top),
                    left: parseInt($dragBoxPos.left),
                    items: []
                };

                $sortDrag.find('.dragItem').each(function (index) {
                    var $dragItem = $(this),
                        $dragItemPos = $dragItem.position();
                    dataItem.items.push({
                        sn: $dragItem.attr('data-sn'),
                        sequence: index + 1
                    });
                });

                data.push(dataItem)
            }

            return data;
        },

        miscSortDragData: function () {
            var $miscSortDrag = $(this),
                $sortDragList = $miscSortDrag.find('.sortDrag');

            var data = [];
            for (var i = 0; i < $sortDragList.size(); i++) {
                var $sortDrag = $sortDragList.eq(i)

                var dataItem = {
                    iskey: $sortDrag.attr('data-iskey'),
                    sn: $sortDrag.attr('data-sn'),
                    name: $sortDrag.attr('data-name') || '',
                    sequence: i + 1,
                    items: []
                };

                $sortDrag.find('.dragItem').each(function (index) {
                    var $dragItem = $(this);
                    var itemData = {
                        iskey: $dragItem.attr('data-iskey'),
                        sn: $dragItem.attr('data-sn'),
                        name: $dragItem.attr('data-name') || '',
                        sequence: index + 1
                    };

                    $dragItem.find('.ctl-label :checkbox').each(function () {
                        var $lable = $(this), lableName = $lable.attr('name');
                        if (lableName) {
                            itemData[lableName] = $lable.is(":checked");
                        }
                    });

                    dataItem.items.push(itemData);
                });

                data.push(dataItem);
            }

            return data;
        },

        miscSortDrag: function (options) {
            var op = $.extend({
                cursor: 'move', // selector 鐨勯紶鏍囨墜鍔�
                sortBoxs: 'dl.miscSortDrag', //鎷栧姩鎺掑簭椤圭埗瀹瑰櫒
                replace: false, //2涓猻ortBox涔嬮棿鎷栧姩鏇挎崲
                items: '> dt .dragItem', //鎷栧姩鎺掑簭椤归€夋嫨鍣�
                zIndex: 1000
            }, options);

            return this.each(function () {

                var $sortBox = $(this);

                $sortBox.find(op.items).each(function (i) {
                    var $item = $(this);

                    $item.mousedown(function (event) {
                        DWZ.miscDrag.startSortDrag($sortBox, $item, event, op);

                        event.preventDefault();
                    });
                });

            });
        }
    });
})(jQuery);